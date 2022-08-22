package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.enums.ReviewType;
import com.venson.eduservice.entity.dto.CoursePreviewVo;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.mapper.EduReviewMapper;
import com.venson.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-16
 */
@Service
@Slf4j
public class EduReviewServiceImp extends ServiceImpl<EduReviewMapper, EduReview> implements EduReviewService {
    private final String msgDelimiter = "/?newMsg?/";

    @Autowired
    private EduSectionService sectionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduChapterMarkdownService chapterMdService;
    @Autowired
    private EduSectionMarkdownService sectionMdService;

    @Autowired
    private EduChapterPublishedService chapterPublishedService;
    @Autowired
    private EduSectionPublishedService sectionPublishedService;
    @Autowired
    private EduChapterPublishedMdService chapterPublishedMdService;
    @Autowired
    private EduSectionPublishedMdService sectionPublishedMdService;

    @Autowired
    private EduResearchService researchService;
    @Autowired
    private EduMethodologyService methodologyService;
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduCoursePublishedService coursePublishedService;
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduCourseDescriptionPublishedService courseDescriptionPublishedService;


    @Transactional
    @Override
    public void requestReviewBySectionId(Long sectionId, ReviewApplyVo reviewVo) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.SECTION)
                .eq(EduReview::getRefId, sectionId)
                .in(EduReview::getStatus, ReviewStatus.REJECTED);
        EduReview one = baseMapper.selectOne(wrapper);
        String newMsg = getMsg(reviewVo, "applied");
        if (one == null) {
            EduReview review = new EduReview();
            review.setRequestMemberId(reviewVo.getId());
            review.setRequestMemberName(reviewVo.getName());
            review.setRequestMsg(newMsg);
            review.setRefIdCourse(reviewVo.getCourseId());
            review.setRefType(ReviewType.SECTION);
            review.setRefId(sectionId);
            review.setStatus(ReviewStatus.APPLIED);
            baseMapper.insert(review);
        } else {
            String oldMsg = one.getRequestMsg();
            String msg = oldMsg + msgDelimiter + newMsg;
            one.setRequestMsg(msg);
            one.setRequestMemberName(reviewVo.getName());
            one.setRequestMemberId(reviewVo.getId());
            one.setStatus(ReviewStatus.APPLIED);
            baseMapper.updateById(one);
        }
        EduSection section = sectionService.getById(sectionId);
        section.setReview(ReviewStatus.APPLIED);
        sectionService.updateById(section);
    }

    @Transactional
    @Override
    public void requestReviewByChapterId(Long chapterId, ReviewApplyVo reviewVo) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.CHAPTER)
                .eq(EduReview::getRefId, chapterId)
                .in(EduReview::getStatus, ReviewStatus.REJECTED);
        EduReview one = baseMapper.selectOne(wrapper);
        String newMsg = getMsg(reviewVo, "applied");
        if (one == null) {
            EduReview review = new EduReview();
            review.setRequestMemberId(reviewVo.getId());
            review.setRequestMemberName(reviewVo.getName());
            review.setRequestMsg(newMsg);
            review.setRefIdCourse(reviewVo.getCourseId());
            review.setRefType(ReviewType.CHAPTER);
            review.setRefId(chapterId);
            review.setStatus(ReviewStatus.APPLIED);
            baseMapper.insert(review);
        } else {
            String oldMsg = one.getRequestMsg();
            String msg = oldMsg + msgDelimiter + newMsg;
            one.setRequestMsg(msg);
            one.setRequestMemberName(reviewVo.getName());
            one.setRequestMemberId(reviewVo.getId());
            one.setStatus(ReviewStatus.APPLIED);
            baseMapper.updateById(one);
        }
        EduChapter chapter = chapterService.getById(chapterId);
        chapter.setReview(ReviewStatus.APPLIED);
        chapterService.updateById(chapter);
    }

    @Override
    public void rejectReviewByChapterId(Long chapterId, ReviewApplyVo reviewVo) {
        EduChapter chapter = chapterService.getById(chapterId);
        if (chapter.getReview() == ReviewStatus.APPLIED)
            throw new CustomizedException(30000, "no relevant review available");
        chapter.setReview(ReviewStatus.REJECTED);
        chapterService.updateById(chapter);
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(EduReview::getRefType, ReviewType.CHAPTER)
                .eq(EduReview::getRefId, chapterId);
        EduReview review = baseMapper.selectOne(wrapper);
        String rejMsg = getMsg(reviewVo, "rejected");
        review.setStatus(ReviewStatus.REJECTED);
        review.setReviewMemberId(review.getId());
        review.setReviewMemberName(reviewVo.getName());
        review.setReviewMsg(review.getReviewMsg() + msgDelimiter + rejMsg);
        baseMapper.update(null, wrapper);
    }

    @Override
    public void rejectReviewBySectionId(Long sectionId, ReviewApplyVo reviewVo) {
        EduSection section = sectionService.getById(sectionId);
        section.setReview(ReviewStatus.REJECTED);
        sectionService.updateById(section);
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(EduReview::getRefType, ReviewType.SECTION)
                .eq(EduReview::getRefId, sectionId);
        EduReview review = baseMapper.selectOne(wrapper);
        String rejMsg = getMsg(reviewVo, "rejected");
        review.setStatus(ReviewStatus.REJECTED);
        review.setReviewMemberId(review.getId());
        review.setReviewMemberName(reviewVo.getName());
        review.setReviewMsg(review.getReviewMsg() + msgDelimiter + rejMsg);
        baseMapper.update(null, wrapper);
    }

    @Transactional
    @Override
    public void passReviewByChapterId(Long chapterId, ReviewApplyVo reviewVo) {
        EduChapter chapter = chapterService.getById(chapterId);
        if (chapter.getIsRemoveAfterReview()) {
            // remove chapter
            chapterService.removeById(chapterId);
            chapterMdService.removeById(chapterId);
            chapterPublishedService.removeById(chapterId);
            chapterPublishedMdService.removeById(chapterId);
            // remove sections
            LambdaQueryWrapper<EduSection> sectionWrapper = new LambdaQueryWrapper<>();
            sectionWrapper.eq(EduSection::getChapterId, chapterId);
            List<EduSection> sectionList = sectionService.list(sectionWrapper);
            List<Long> sectionIdList = sectionList.parallelStream().map(EduSection::getId).collect(Collectors.toList());
            sectionService.removeBatchByIds(sectionIdList);
            sectionMdService.removeBatchByIds(sectionIdList);
            sectionPublishedService.removeBatchByIds(sectionIdList);
            sectionPublishedMdService.removeBatchByIds(sectionIdList);
        } else {
            chapter.setReview(ReviewStatus.FINISHED);
            chapter.setIsPublished(true);
            chapterService.updateById(chapter);
            // save or update section to table edu_chapter_published
            EduChapterPublished chapterPublished = new EduChapterPublished();
            BeanUtils.copyProperties(chapter, chapterPublished);
            chapterPublishedService.saveOrUpdate(chapterPublished);
            // save or update section markdown to table edu_chapter_published_md
            EduChapterPublishedMd publishedMd = new EduChapterPublishedMd();
            EduChapterMarkdown markdown = chapterMdService.getById(chapterId);
            publishedMd.setId(markdown.getId());
            publishedMd.setMarkdown(markdown.getMarkdown());
            chapterPublishedMdService.saveOrUpdate(publishedMd);
        }
        // update chapter review info in table edu_review
        String passMsg = getMsg(reviewVo, "passed");
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.CHAPTER)
                .eq(EduReview::getRefId, chapterId)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED);
        EduReview review = baseMapper.selectOne(wrapper);
        review.setStatus(ReviewStatus.FINISHED);
        review.setReviewMemberId(reviewVo.getId());
        review.setReviewMemberName(reviewVo.getName());
        review.setReviewMsg(review.getReviewMsg() + msgDelimiter + passMsg);
        baseMapper.updateById(review);
    }

    @Transactional
    @Override
    public void passReviewBySectionId(Long sectionId, ReviewApplyVo reviewVo) {
        EduSection section = sectionService.getById(sectionId);
        if (section.getIsRemoveAfterReview()) {
            sectionService.removeById(sectionId);
            sectionMdService.removeById(sectionId);
            sectionPublishedService.removeById(sectionId);
            sectionPublishedMdService.removeById(sectionId);
        } else {
            section.setReview(ReviewStatus.FINISHED);
            section.setIsPublished(true);
            sectionService.updateById(section);
            // save or update section to table edu_section_published
            EduSectionPublished sectionPublished = new EduSectionPublished();
            BeanUtils.copyProperties(section, sectionPublished);
            sectionPublishedService.saveOrUpdate(sectionPublished);

            // save or update section markdown to table edu_section_published_md
            EduSectionPublishedMd publishedMd = new EduSectionPublishedMd();
            EduSectionMarkdown markdown = sectionMdService.getById(sectionId);
            publishedMd.setId(markdown.getId());
            publishedMd.setMarkdown(markdown.getMarkdown());
            sectionPublishedMdService.saveOrUpdate(publishedMd);
        }
        // update chapter review info in table edu_review
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.SECTION)
                .eq(EduReview::getRefId, sectionId)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED);
        EduReview review = baseMapper.selectOne(wrapper);
        setReviewStatus(review, ReviewStatus.FINISHED, reviewVo);
        baseMapper.updateById(review);
    }

    @Transactional
    @Override
    public void passReviewByCourseId(Long courseId, ReviewApplyVo reviewVo) {
        EduCourse course = courseService.getById(courseId);
        LambdaQueryWrapper<EduChapter> chapterWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<EduSection> sectionWrapper = new LambdaQueryWrapper<>();
        // 1. if the course is marked remove after review
        if (course.getIsRemoveAfterReview()) {
            // get the chapter id list that are in the corresponding course ID.
            chapterWrapper.eq(EduChapter::getCourseId, courseId).select(EduChapter::getId);
            List<EduChapter> chapterList = chapterService.list(chapterWrapper);
            List<Long> chapterIdList = chapterList.parallelStream().map(EduChapter::getId).collect(Collectors.toList());

            // get the section id list that are in the corresponding course ID.
            sectionWrapper.eq(EduSection::getCourseId, courseId).select(EduSection::getId);
            List<EduSection> sectionList = sectionService.list(sectionWrapper);
            List<Long> sectionIdList = sectionList.parallelStream().map(EduSection::getId).collect(Collectors.toList());

            // remove chapter
            chapterService.removeBatchByIds(chapterIdList);
            chapterMdService.removeBatchByIds(chapterIdList);
            chapterPublishedService.removeBatchByIds(chapterIdList);
            chapterPublishedMdService.removeBatchByIds(chapterIdList);
            // remove section
            sectionService.removeBatchByIds(sectionIdList);
            sectionMdService.removeBatchByIds(sectionIdList);
            sectionPublishedService.removeBatchByIds(sectionIdList);
            sectionPublishedMdService.removeBatchByIds(sectionIdList);
            // remove course
            courseService.removeById(courseId);
            coursePublishedService.removeById(courseId);
            courseDescriptionService.removeById(courseId);
            courseDescriptionPublishedService.removeById(courseId);
        } else {
            // 2. publish all chapters, sections ,course and course description that are in ReviewStatus.APPLIED status

            // 2.1 chapter
            chapterWrapper.eq(EduChapter::getCourseId, courseId)
                    .eq(EduChapter::getReview, ReviewStatus.APPLIED);
            List<EduChapter> chapterList = chapterService.list(chapterWrapper);
            // handle all chapters that with review mark if chapterList is not null
            if (chapterList.size() != 0) {
                List<EduChapterPublished> chapterPublishedList = new ArrayList<>(chapterList.size());
                //get the chapters are marked to remove after review
                List<Long> chapterToRemove = chapterList.parallelStream()
                        .filter(EduChapter::getIsRemoveAfterReview).map(EduChapter::getId)
                        .collect(Collectors.toList());
                // get chapters to be published
                List<EduChapter> chapterToPublish = chapterList.parallelStream()
                        .filter(o -> !o.getIsRemoveAfterReview())
                        .collect(Collectors.toList());


                List<Long> chapterIdList = chapterToPublish.parallelStream()
                        .map(EduChapter::getId).collect(Collectors.toList());

                // publish chapters
                if (chapterToPublish.size() != 0) {
                    chapterToPublish.parallelStream().forEach(o -> {
                        o.setReview(ReviewStatus.FINISHED);
                        o.setIsModified(false);
                        o.setIsPublished(true);
                    });
                    // update chapter info in edu_chapter table after review
                    chapterService.updateBatchById(chapterToPublish);
                    chapterToPublish.parallelStream().forEach(o -> {
                        EduChapterPublished temp = new EduChapterPublished();
                        BeanUtils.copyProperties(o, temp);
                        chapterPublishedList.add(temp);
                    });
                    // save or update chapter to edu_chapter_published table after review
                    chapterPublishedService.saveOrUpdateBatch(chapterPublishedList);
                    // save or update chapter to edu_chapter_published_md table after review
                    chapterPublishMd(chapterIdList);
                }

                // remove chapter by id which are marked
                if (chapterToRemove.size() != 0) {
                    chapterRemoveBatch(chapterToRemove);
                    // end of chapter ops
                }
            }

            // 2.2 section
            sectionWrapper.eq(EduSection::getCourseId, courseId)
                    .eq(EduSection::getReview, ReviewStatus.APPLIED);
            List<EduSection> sectionList = sectionService.list(sectionWrapper);
            if (sectionList.size() != 0) {
                //publish all sections
                List<EduSectionPublished> sectionPublishedList = new ArrayList<>(sectionList.size());

                List<Long> sectionToRemove = sectionList.parallelStream()
                        .filter(EduSection::getIsRemoveAfterReview).map(EduSection::getId)
                        .collect(Collectors.toList());
                List<EduSection> sectionToPublish = sectionList.parallelStream()
                        .filter(o -> !o.getIsRemoveAfterReview())
                        .collect(Collectors.toList());

                List<Long> sectionIdList = sectionToPublish.parallelStream()
                        .map(EduSection::getId).collect(Collectors.toList());

                if (sectionToPublish.size() != 0) {
                    sectionToPublish.parallelStream().forEach(o -> {
                        EduSectionPublished temp = new EduSectionPublished();
                        BeanUtils.copyProperties(o, temp);
                        sectionPublishedList.add(temp);
                    });
                    sectionPublishedService.saveOrUpdateBatch(sectionPublishedList);
                    sectionToPublish.parallelStream().forEach(o -> {
                        o.setReview(ReviewStatus.FINISHED);
                        o.setIsModified(false);
                        o.setIsPublished(true);
                    });
                    sectionService.updateBatchById(sectionToPublish);
                    sectionPublishMd(sectionIdList);
                }

                // remove section by id which are marked
                if (sectionToRemove.size() != 0) {
                    sectionRemoveBatch(sectionToRemove);
                }
            }
            // 2.3 course
            EduCourseDescription description = courseDescriptionService.getById(courseId);
            course.setReview(ReviewStatus.FINISHED);
            EduCoursePublished coursePublished = new EduCoursePublished();
            BeanUtils.copyProperties(course,coursePublished);
            EduCourseDescriptionPublished descriptionPublished = new EduCourseDescriptionPublished();
            BeanUtils.copyProperties(description,descriptionPublished);
            coursePublishedService.saveOrUpdate(coursePublished);
            courseDescriptionPublishedService.saveOrUpdate(descriptionPublished);


        }


        // 3. update all review status
        LambdaQueryWrapper<EduReview> reviewWrapper = new LambdaQueryWrapper<>();
        reviewWrapper.eq(EduReview::getRefIdCourse, courseId)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED);
        List<EduReview> reviewList = baseMapper.selectList(reviewWrapper);

        // set to FINISHED
        reviewList.parallelStream().forEach(o -> setReviewStatus(o, ReviewStatus.FINISHED, reviewVo));
        updateBatchById(reviewList);

    }

    @Transactional
    @Override
    public void requestReviewByCourseId(Long courseId, ReviewApplyVo reviewVo) {
        //1. check if the course is already under review .
        EduCourse course = courseService.getById(courseId);
        if(course.getReview() == ReviewStatus.APPLIED){
            throw new CustomizedException(30000, "the course already has a review applied");
        }
        //2.1 get the chapters of the course, which are modified and not requested yet.
        LambdaQueryWrapper<EduChapter> chapterWrapper = new LambdaQueryWrapper<>();
        chapterWrapper.eq(EduChapter::getIsModified, true)
                .ne(EduChapter::getReview, ReviewStatus.APPLIED);
        List<EduChapter> chapterList = chapterService.list(chapterWrapper);

        //2.2 get the sections of the course, which are modified and not requested yet.
        LambdaQueryWrapper<EduSection> sectionWrapper = new LambdaQueryWrapper<>();
        sectionWrapper.eq(EduSection::getIsModified, true)
                .ne(EduSection::getReview, ReviewStatus.APPLIED);
        List<EduSection> sectionList = sectionService.list(sectionWrapper);

        //3 prepare reviewToAddList to sore review for each chapter and section
        List<EduReview> reviewToAddList = new ArrayList<>(chapterList.size());
        // 3.1 add review for chapters
        String msg = getMsg(reviewVo, ReviewStatus.APPLIED.getDesc());
        chapterList.parallelStream().forEach(o -> {
            o.setReview(ReviewStatus.APPLIED);
            EduReview temp = new EduReview(ReviewStatus.APPLIED,
                    reviewVo.getId(),
                    reviewVo.getName(),
                    ReviewType.CHAPTER,
                    o.getId(),
                    courseId,
                    msg);
            reviewToAddList.add(temp);

        });
        // 3.2 add reviews for sections
        sectionList.parallelStream().forEach(o -> {
            o.setReview(ReviewStatus.APPLIED);
            EduReview temp = new EduReview(ReviewStatus.APPLIED,
                    reviewVo.getId(),
                    reviewVo.getName(),
                    ReviewType.SECTION,
                    o.getId(),
                    courseId,
                    msg);
            reviewToAddList.add(temp);
        });
        //3.3 add  review for course
        EduReview courseReview = new EduReview(ReviewStatus.APPLIED,
                reviewVo.getId(),
                reviewVo.getName(),
                ReviewType.COURSE,
                courseId,
                courseId,
                msg);
        reviewToAddList.add(courseReview);
        chapterService.updateBatchById(chapterList);
        sectionService.updateBatchById(sectionList);
        saveBatch(reviewToAddList);

    }

    @Transactional
    @Override
    public void rejectReviewByCourseId(Long courseId, ReviewApplyVo reviewVo) {

        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getStatus, ReviewStatus.APPLIED)
                .eq(EduReview::getRefIdCourse, courseId);
        List<EduReview> reviewList = baseMapper.selectList(wrapper);
        reviewList.parallelStream().forEach(o -> setReviewStatus(o, ReviewStatus.REJECTED, reviewVo));
        boolean result = updateBatchById(reviewList);
        if (!result) throw new CustomizedException(30000, "no relevant review available");

        LambdaUpdateWrapper<EduChapter> chapterWrapper = new LambdaUpdateWrapper<>();
        chapterWrapper.eq(EduChapter::getCourseId, courseId)
                .eq(EduChapter::getReview, ReviewStatus.APPLIED)
                .set(EduChapter::getReview, ReviewStatus.REJECTED);
        chapterService.update(chapterWrapper);
        //
        LambdaUpdateWrapper<EduSection> sectionWrapper = new LambdaUpdateWrapper<>();
        sectionWrapper.eq(EduSection::getCourseId, courseId)
                .eq(EduSection::getReview, ReviewStatus.APPLIED)
                .set(EduSection::getReview, ReviewStatus.REJECTED);
        sectionService.update(sectionWrapper);
    }

    @Override
    public Map<String, Object> getPageReviewList(Integer pageNum, Integer limit) {
        LambdaQueryWrapper<EduReview> wrapper = new QueryWrapper<EduReview>().select("distinct ref_id_course").lambda().eq(EduReview::getStatus, ReviewStatus.APPLIED)
                .isNotNull(EduReview::getRefIdCourse);
        List<EduReview> reviewList = baseMapper.selectList(wrapper);
        List<Long> courseIdList = reviewList.stream().map(EduReview::getRefIdCourse).collect(Collectors.toList());
        if (courseIdList.size() == 0) {
            return null;
        }
        QueryWrapper<CoursePreviewVo> courseWrapper = new QueryWrapper<>();
        courseWrapper.in("c.id", courseIdList);
        return courseService.getPageReviewCoursePreviewVo(pageNum, limit, courseWrapper);
    }

    @Transactional
    @Override
    public void requestReviewByResearchId(Long id, ReviewApplyVo applyVo) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.RESEARCH)
                .eq(EduReview::getRefId, id)
                .eq(EduReview::getStatus, ReviewStatus.REJECTED);
        EduReview review;
        review = baseMapper.selectOne(wrapper);
        if (review != null) {
            setReviewStatus(review, ReviewStatus.APPLIED, applyVo);
            baseMapper.updateById(review);
        } else {
            review = new EduReview();
            setReviewStatus(review, ReviewStatus.APPLIED, applyVo);
            baseMapper.insert(review);
        }
        LambdaUpdateWrapper<EduResearch> researchWrapper = new LambdaUpdateWrapper<>();
        researchWrapper.eq(EduResearch::getId, id)
                .set(EduResearch::getStatus, ReviewStatus.APPLIED);
        researchService.update(researchWrapper);
    }

    @Override
    public void passReviewByResearchId(Long id, ReviewApplyVo applyVo) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.RESEARCH)
                .eq(EduReview::getRefId, id)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED);
        EduReview review = baseMapper.selectOne(wrapper);
        if (review == null) {
            throw new CustomizedException(30000, "No applied review available");
        }
        setReviewStatus(review, ReviewStatus.FINISHED, applyVo);
        baseMapper.updateById(review);
        // update Edu_research table
        LambdaQueryWrapper<EduResearch> researchWrapper = new LambdaQueryWrapper<>();
        researchWrapper.eq(EduResearch::getId, id);
        EduResearch research = researchService.getOne(researchWrapper);
        // update content of the course
        research.setStatus(ReviewStatus.FINISHED);
        research.setIsModified(false);
        research.setIsPublished(true);
        research.setPublishedMd(research.getMarkdown());
        researchService.updateById(research);
    }

    @Override
    public void rejectReviewByResearchId(Long id, ReviewApplyVo reviewVo) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.RESEARCH)
                .eq(EduReview::getRefId, id)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED);
        EduReview review = baseMapper.selectOne(wrapper);
        if (review == null) {
            throw new CustomizedException(30000, "No applied review available");
        }
        setReviewStatus(review, ReviewStatus.REJECTED, reviewVo);
        baseMapper.updateById(review);
        // update Edu_research table
    }
    @Override
    public void requestReviewByMethodologyId(Long id, ReviewApplyVo applyVo) {

        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.METHODOLOGY)
                .eq(EduReview::getRefId, id)
                .eq(EduReview::getStatus, ReviewStatus.REJECTED);
        EduReview review;
        review = baseMapper.selectOne(wrapper);
        if (review != null) {
            setReviewStatus(review, ReviewStatus.APPLIED, applyVo);
            baseMapper.updateById(review);
        } else {
            review = new EduReview();
            setReviewStatus(review, ReviewStatus.APPLIED, applyVo);
            baseMapper.insert(review);
        }
        LambdaUpdateWrapper<EduMethodology> methodologyWrapper = new LambdaUpdateWrapper<>();
        methodologyWrapper.eq(EduMethodology::getId, id)
                .set(EduMethodology::getStatus, ReviewStatus.APPLIED);
        methodologyService.update(methodologyWrapper);
    }

    @Override
    public void passReviewByMethodologyId(Long id, ReviewApplyVo applyVo) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.METHODOLOGY)
                .eq(EduReview::getRefId, id)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED);
        EduReview review = baseMapper.selectOne(wrapper);
        if (review == null) {
            throw new CustomizedException(30000, "No applied review available");
        }
        setReviewStatus(review, ReviewStatus.FINISHED, applyVo);
        baseMapper.updateById(review);
        // update Edu_research table
        LambdaQueryWrapper<EduMethodology> methodologyWrapper = new LambdaQueryWrapper<>();
        methodologyWrapper.eq(EduMethodology::getId, id);
        EduMethodology methodology = methodologyService.getOne(methodologyWrapper);
        // update content of the course
        methodology.setStatus(ReviewStatus.FINISHED);
        methodology.setIsModified(false);
        methodology.setIsPublished(true);
        methodology.setPublishedMd(methodology.getMarkdown());
        methodologyService.updateById(methodology);
    }

    @Override
    public void rejectReviewByMethodologyId(Long id, ReviewApplyVo reviewVo) {

        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.RESEARCH)
                .eq(EduReview::getRefId, id)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED);
        EduReview review = baseMapper.selectOne(wrapper);
        if (review == null) {
            throw new CustomizedException(30000, "No applied review available");
        }
        setReviewStatus(review, ReviewStatus.REJECTED, reviewVo);
        baseMapper.updateById(review);
        // update Edu_methodology table
        LambdaQueryWrapper<EduMethodology> methodologyWrapper = new LambdaQueryWrapper<>();
        methodologyWrapper.eq(EduMethodology::getId, id);
        EduMethodology methodology = methodologyService.getOne(methodologyWrapper);
        // update content of the course
        methodology.setStatus(ReviewStatus.REJECTED);
        methodologyService.updateById(methodology);
    }

    private void chapterRemoveBatch(List<Long> removeIdList) {
        chapterService.removeBatchByIds(removeIdList);
        chapterPublishedService.removeBatchByIds(removeIdList);
        chapterPublishedMdService.removeBatchByIds(removeIdList);
        chapterMdService.removeBatchByIds(removeIdList);
    }

    private void chapterPublishMd(List<Long> publishIdList) {
        List<EduChapterMarkdown> markdownList = chapterMdService.listByIds(publishIdList);
        ArrayList<EduChapterPublishedMd> chapterPublishedMds = new ArrayList<>(markdownList.size());
        markdownList.parallelStream().forEach(o -> {
            EduChapterPublishedMd temp = new EduChapterPublishedMd();
            BeanUtils.copyProperties(o, temp);
            chapterPublishedMds.add(temp);
        });
        chapterPublishedMdService.saveOrUpdateBatch(chapterPublishedMds);
    }

    private void sectionRemoveBatch(List<Long> removeIdList) {
        sectionService.removeBatchByIds(removeIdList);
        sectionPublishedService.removeBatchByIds(removeIdList);
        sectionPublishedMdService.removeBatchByIds(removeIdList);
        sectionMdService.removeBatchByIds(removeIdList);
    }

    private void sectionPublishMd(List<Long> publishIdList) {
        List<EduSectionMarkdown> markdownList = sectionMdService.listByIds(publishIdList);
        ArrayList<EduSectionPublishedMd> sectionPublishedMds = new ArrayList<>(markdownList.size());
        markdownList.parallelStream().forEach(o -> {
            EduSectionPublishedMd temp = new EduSectionPublishedMd();
            BeanUtils.copyProperties(o, temp);
            sectionPublishedMds.add(temp);
        });
        sectionPublishedMdService.saveOrUpdateBatch(sectionPublishedMds);
    }

    @Override
    public String getMsg(ReviewApplyVo reviewVo, String status) {
        return String.join(",", reviewVo.getName(), status,
                reviewVo.getMsg(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }


    public void setReviewStatus(EduReview review, ReviewStatus newStatus, ReviewApplyVo vo) {
        ReviewStatus oldStatus = review.getStatus();
        String msg = getMsg(vo, newStatus.getDesc());
        String requestMsg = review.getRequestMsg();
        String reviewMsg = review.getReviewMsg();
        if (oldStatus == null) {
            review.setRequestMsg(msg);
            review.setStatus(newStatus);
            review.setRequestMemberId(vo.getId());
            review.setRequestMemberName(vo.getName());
            return;
        }

        switch (oldStatus) {
            case NONE:
                if (newStatus == ReviewStatus.APPLIED) {
                    review.setRequestMsg(msg);
                } else throw new CustomizedException(30000, "unsupported status change");
                break;
            case APPLIED:
                if (newStatus == ReviewStatus.FINISHED || newStatus == ReviewStatus.REJECTED) {
                    if (ObjectUtils.isEmpty(reviewMsg)) {
                        review.setReviewMsg(msg);
                    } else {
                        review.setReviewMsg(reviewMsg + msgDelimiter + msg);
                    }
                } else throw new CustomizedException(30000, "unsupported status change");
                break;
            case REJECTED:
                if (newStatus == ReviewStatus.APPLIED) {
                    review.setRequestMsg(requestMsg + msgDelimiter + msg);
                } else throw new CustomizedException(30000, "unsupported status change");
                break;
        }
        review.setRequestMemberId(vo.getId());
        review.setRequestMemberName(vo.getName());
        review.setStatus(newStatus);
    }

    @Override
    public List<EduReview> getReviewByMethodologyId(Long id) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.METHODOLOGY)
                .eq(EduReview::getRefId,id)
                .orderByAsc(EduReview::getId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<EduReview> getReviewByResearchId(Long id) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.RESEARCH)
                        .eq(EduReview::getRefId,id)
                .orderByAsc(EduReview::getId);
        return baseMapper.selectList(wrapper);
    }

}
