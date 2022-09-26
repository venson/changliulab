package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.chapter.CourseTreeNodeVo;
import com.venson.eduservice.entity.dto.ChapterDTO;
import com.venson.eduservice.mapper.EduChapterMapper;
import com.venson.eduservice.service.*;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@Service
@Slf4j
public class EduChapterServiceImp extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    private final EduSectionService sectionService;
    private final EduChapterMarkdownService markdownService;

    public EduChapterServiceImp(EduSectionService sectionService,EduChapterMarkdownService markdownService) {
        this.sectionService = sectionService;
        this.markdownService = markdownService;
    }


    @Override
    public List<CourseTreeNodeVo> getCourseTreeByCourseId(Long courseId) {

        //get chapter info as parent node
        LambdaQueryWrapper<EduChapter> wrapperChapter = new LambdaQueryWrapper<>();
        wrapperChapter.eq(EduChapter::getCourseId, courseId);
        wrapperChapter.orderBy(true,true,EduChapter::getSort,EduChapter::getId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapperChapter);

        List<CourseTreeNodeVo>chapterNodeList = chapterList.parallelStream()
                .map(o -> new CourseTreeNodeVo(o.getId(),
                        o.getTitle(),o.getReview(),
                        o.getIsPublished(),o.getIsModified(),o.getIsRemoveAfterReview()))
                .collect(Collectors.toList());


        //get section info as children node
        LambdaQueryWrapper<EduSection> wrapperSection = new LambdaQueryWrapper<>();
        wrapperSection.eq(EduSection::getCourseId, courseId).
                orderBy(true, true, EduSection::getChapterId, EduSection::getSort);
        List<EduSection> sectionList = sectionService.list(wrapperSection);

        // group section  by chapter node and
        Map<Long, List<CourseTreeNodeVo>> sectionGroupMap = sectionList.parallelStream().
                collect(Collectors.groupingBy(EduSection::getChapterId,
                        Collectors.mapping(o -> new CourseTreeNodeVo(o.getId(),
                                        o.getTitle(),o.getReview(),o.getIsPublished(),o.getIsModified(),o.getIsRemoveAfterReview()),
                                Collectors.toList())));

        chapterNodeList.parallelStream().forEach(o-> o.setChildren(sectionGroupMap.get(o.getId())));
        return chapterNodeList;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteChapterSectionByCourseId(Long courseId) {
        LambdaUpdateWrapper<EduChapter> chapterWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<EduSection> sectionWrapper = new LambdaUpdateWrapper<>();
        chapterWrapper.eq(EduChapter::getCourseId,courseId)
                .set(EduChapter::getIsRemoveAfterReview, true);
        sectionWrapper.eq(EduSection::getCourseId,courseId)
                .set(EduSection::getIsRemoveAfterReview, true);
        baseMapper.update(null,chapterWrapper);
        sectionService.update(sectionWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result removeChapterById(Long chapterId) {
        LambdaUpdateWrapper<EduChapter> chapterWrapper = new LambdaUpdateWrapper<>();
        chapterWrapper.eq(EduChapter::getId,chapterId)
                .set(EduChapter::getIsRemoveAfterReview, true);
        baseMapper.update(null,chapterWrapper);
        LambdaUpdateWrapper<EduSection> sectionWrapper = new LambdaUpdateWrapper<>();
        sectionWrapper.eq(EduSection::getChapterId, chapterId)
                .set(EduSection::getIsRemoveAfterReview, true);
        sectionService.update(sectionWrapper);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateChapterById(Long chapterId, ChapterDTO chapter) {
        EduChapter eduChapter = baseMapper.selectById(chapterId);
        eduChapter.setIsModified(true);
        EduChapterMarkdown markdown = markdownService.getById(chapterId);
        eduChapter.setTitle(chapter.getTitle());
        markdown.setMarkdown(chapter.getMarkdown());
        baseMapper.updateById(eduChapter);
        markdownService.updateById(markdown);
    }

    @Override
    public ChapterDTO getChapterDTOById(Long chapterId) {
        EduChapter chapter =baseMapper.selectById(chapterId);
        EduChapterMarkdown chapterMd = markdownService.getById(chapterId);
        ChapterDTO chapterDTO = new ChapterDTO();
        BeanUtils.copyProperties(chapter, chapterDTO);
        chapterDTO.setMarkdown(chapterMd.getMarkdown());
        return chapterDTO;
    }

    /**
     *  add chapter
     * @return RMessage with chapterId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addChapter(ChapterDTO chapterDTO) {
        if(!ObjectUtils.isEmpty(chapterDTO.getId())){
            throw new CustomizedException(40000,"chapter already exist");
        }
        EduChapter chapter = new EduChapter();
        EduChapterMarkdown chapterMarkdown = new EduChapterMarkdown();
        BeanUtils.copyProperties(chapterDTO,chapter);
        baseMapper.insert(chapter);
        chapterMarkdown.setId(chapter.getId());
        chapterMarkdown.setMarkdown(chapterDTO.getMarkdown());
        markdownService.save(chapterMarkdown);
        return Result.success().data("id",chapter.getId());
    }

    @Override
    public Long addEmptyChapter(Long courseId) {
        // automatic set sort, get the count of the courses
        LambdaQueryWrapper<EduChapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduChapter::getCourseId, courseId);
        Long chapterCount = baseMapper.selectCount(wrapper);
        // add empty chapter
        EduChapter eduChapter = new EduChapter();
        eduChapter.setTitle("New Chapter");
        eduChapter.setCourseId(courseId);
        eduChapter.setSort(Math.toIntExact(++chapterCount));
        baseMapper.insert(eduChapter);
        EduChapterMarkdown chapterMarkdown = new EduChapterMarkdown();
        chapterMarkdown.setId(eduChapter.getId());
        chapterMarkdown.setMarkdown("Please Edit");
        markdownService.save(chapterMarkdown);
        return eduChapter.getId();
    }

}
