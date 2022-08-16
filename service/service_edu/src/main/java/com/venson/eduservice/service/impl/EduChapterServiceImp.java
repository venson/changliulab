package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.chapter.CourseTreeNodeVo;
import com.venson.eduservice.entity.vo.ChapterVo;
import com.venson.eduservice.mapper.EduChapterMapper;
import com.venson.eduservice.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void removeChapterById(Long chapterId) {
        LambdaUpdateWrapper<EduChapter> chapterWrapper = new LambdaUpdateWrapper<>();
        chapterWrapper.eq(EduChapter::getId,chapterId)
                .set(EduChapter::getIsRemoveAfterReview, true);
        baseMapper.update(null,chapterWrapper);
        LambdaUpdateWrapper<EduSection> sectionWrapper = new LambdaUpdateWrapper<>();
        sectionWrapper.eq(EduSection::getChapterId, chapterId)
                .set(EduSection::getIsRemoveAfterReview, true);
        sectionService.update(sectionWrapper);
    }

    @Transactional
    @Override
    public void updateChapterById(Long chapterId, ChapterVo chapter) {
        EduChapter eduChapter = baseMapper.selectById(chapterId);
        EduChapterMarkdown markdown = markdownService.getById(chapterId);
        eduChapter.setTitle(chapter.getTitle());
        markdown.setMarkdown(chapter.getMarkdown().getMarkdown());
        baseMapper.updateById(eduChapter);
        markdownService.updateById(markdown);
    }

}
