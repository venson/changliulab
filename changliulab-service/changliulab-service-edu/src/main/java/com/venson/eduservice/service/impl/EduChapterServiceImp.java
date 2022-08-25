package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.chapter.CourseTreeNodeVo;
import com.venson.eduservice.entity.dto.ChapterDTO;
import com.venson.eduservice.mapper.EduChapterMapper;
import com.venson.eduservice.service.*;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
    private final EduChapterPublishedService chapterPublishedService;
    private final EduChapterPublishedMdService chapterPublishedMdService;
    private final EduChapterMarkdownService markdownService;

    public EduChapterServiceImp(EduSectionService sectionService, EduChapterPublishedService chapterPublishedService, EduChapterPublishedMdService chapterPublishedMdService, EduChapterMarkdownService markdownService) {
        this.sectionService = sectionService;
        this.chapterPublishedService = chapterPublishedService;
        this.chapterPublishedMdService = chapterPublishedMdService;
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
        List<CourseTreeNodeVo> finalList = new ArrayList<>();

        // group section  by chapter node and
        Map<Long, List<CourseTreeNodeVo>> sectionGroupMap = sectionList.parallelStream().
                collect(Collectors.groupingBy(EduSection::getChapterId,
                        Collectors.mapping(o -> new CourseTreeNodeVo(o.getId(),
                                        o.getTitle(),o.getReview(),o.getIsPublished(),o.getIsModified(),o.getIsRemoveAfterReview()),
                                Collectors.toList())));

        chapterNodeList.parallelStream().forEach(o-> o.setChildren(sectionGroupMap.get(o.getId())));
        return chapterNodeList;

//        for (EduChapter chapter :
//                chapterList) {
//            CourseTreeNodeVo chapterVoTemp = new CourseTreeNodeVo();
//            BeanUtils.copyProperties(chapter, chapterVoTemp);
//            List<CourseTreeNodeVo> sectionNodeList = sectionGroupMap.get(chapterVoTemp.getId());
//            chapterVoTemp.setChildren(sectionNodeList);
//            finalList.add(chapterVoTemp);
//        }
//
//        return finalList;
    }


    @Transactional
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
    public RMessage removeChapterById(Long chapterId) {
        LambdaUpdateWrapper<EduChapter> chapterWrapper = new LambdaUpdateWrapper<>();
        chapterWrapper.eq(EduChapter::getId,chapterId)
                .set(EduChapter::getIsRemoveAfterReview, true);
        baseMapper.update(null,chapterWrapper);
        LambdaUpdateWrapper<EduSection> sectionWrapper = new LambdaUpdateWrapper<>();
        sectionWrapper.eq(EduSection::getChapterId, chapterId)
                .set(EduSection::getIsRemoveAfterReview, true);
        sectionService.update(sectionWrapper);
        return RMessage.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateChapterById(Long chapterId, ChapterDTO chapter) {
        EduChapter eduChapter = baseMapper.selectById(chapterId);
        EduChapterMarkdown markdown = markdownService.getById(chapterId);
        eduChapter.setTitle(chapter.getTitle());
        markdown.setMarkdown(chapter.getMarkdown());
        baseMapper.updateById(eduChapter);
        markdownService.updateById(markdown);
    }

    @Override
    @Cacheable()
    public RMessage getChapterDTOById(Long chapterId) {
        EduChapter chapter =baseMapper.selectById(chapterId);
        EduChapterMarkdown chapterMd = markdownService.getById(chapterId);
        ChapterDTO chapterDTO = new ChapterDTO();
        BeanUtils.copyProperties(chapter, chapterDTO);
        chapterDTO.setMarkdown(chapterMd.getMarkdown());
        return RMessage.ok().data(chapterDTO);
    }

    /**
     *  add chapter
     * @return RMessage with chapterId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RMessage addChapter(ChapterDTO chapterDTO) {
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
        return RMessage.ok().data("id",chapter.getId());
    }

}
