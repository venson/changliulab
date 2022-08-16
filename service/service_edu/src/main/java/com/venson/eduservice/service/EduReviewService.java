package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduReview;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.vo.ReviewApplyVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-16
 */
public interface EduReviewService extends IService<EduReview> {

    void requestReviewBySectionId(Long sectionId, ReviewApplyVo reviewVo);

    void requestReviewByChapterId(Long chapterId, ReviewApplyVo reviewVo);

    void rejectReviewByChapterId(Long chapterId, ReviewApplyVo reviewVo);
    void rejectReviewBySectionId(Long sectionId, ReviewApplyVo reviewVo);

    void passReviewByChapterId(Long chapterId, ReviewApplyVo reviewVo);

    void passReviewBySectionId(Long sectionId, ReviewApplyVo reviewVo);

    void passReviewByCourseId(Long courseId, ReviewApplyVo reviewVo);

    void requestReviewByCourseId(Long courseId, ReviewApplyVo reviewVo);

    void rejectReviewByCourseId(Long courseId, ReviewApplyVo reviewVo);

    Map<String, Object> getPageReviewList(Integer pageNum, Integer limit);

    void requestReviewByResearchId(Long id, ReviewApplyVo applyVo);

    void passReviewByResearchId(Long id, ReviewApplyVo applyVo);

    void rejectReviewByResearchId(Long id, ReviewApplyVo reviewVo);

    void requestReviewByMethodologyId(Long id, ReviewApplyVo applyVo);

    void passReviewByMethodologyId(Long id, ReviewApplyVo applyVo);

    void rejectReviewByMethodologyId(Long id, ReviewApplyVo reviewVo);

    String getMsg(ReviewApplyVo reviewVo, String status);

    void setReviewStatus(EduReview review, ReviewStatus newStatus, ReviewApplyVo vo);

    List<EduReview> getReviewByResearchId(Long id);
}
