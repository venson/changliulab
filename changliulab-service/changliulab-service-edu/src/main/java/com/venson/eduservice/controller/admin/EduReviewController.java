package com.venson.eduservice.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.enums.ReviewType;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.service.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  the review controller for course,chapter and section
 * </p>
 *
 * @author venson
 * @since 2022-07-16
 */
@RestController
@RequestMapping("/eduservice/admin/edu-review")
public class EduReviewController {

    private final EduReviewService reviewService;


    public EduReviewController(EduReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("{pageNum}/{limit}")
    @PreAuthorize("hasAuthority('course.review')")
    public Result getPageCourseReviewList(@PathVariable Integer pageNum, @PathVariable Integer limit){
        Page<EduCourse> page = reviewService.getPageReviewList(pageNum,limit);
        return Result.success().data(page);

    }
    /**
     * create review request by chapter id
     * @param chapterId the id of chapter
     * @param reviewVo the info of lab member who requested the review
     * @return success() when success
     */
    @PostMapping("chapter/{chapterId}")
    @PreAuthorize("hasAuthority('course.review.request')")
    public Result requestReviewByChapterId(@PathVariable Long chapterId,
                                           @RequestBody ReviewApplyVo reviewVo){
        reviewService.requestReviewByChapterId(chapterId,reviewVo);
        return Result.success() ;
    }

    /**
     * pass the review and set the review status to finished.
     * @param chapterId the id of chapter
     * @param reviewVo the info of lab member who request the review
     * @return success() when success
     */
    @PutMapping("chapter/{chapterId}")
    @PreAuthorize("hasAuthority('course.review.pass')")
    public Result passReviewByChapterId(@PathVariable Long chapterId,
                                        @RequestBody ReviewApplyVo reviewVo){
        reviewService.passReviewByChapterId(chapterId,reviewVo);
        return Result.success() ;
    }

    /**
     /**
     * reject the review and set the review status to finished.
     * @param chapterId the id of chapter
     * @param reviewVo the info of lab member who request the review
     * @return success() when success
     */
    @PostMapping("chapter/reject/{chapterId}")
    @PreAuthorize("hasAuthority('course.review.reject')")
    public Result rejectReviewByChapterId(@PathVariable Long chapterId,
                                          @RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByChapterId(chapterId,reviewVo);
        return Result.success() ;
    }

    /**
     * get review history of the chapter
     * @param chapterId the id of chapter
     * @return Review list of the chapter
     */
    @GetMapping("chapter/{chapterId}")
    @PreAuthorize("hasAuthority('course.review')")
    public Result getReviewListByChapterId(@PathVariable Long chapterId){
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefId,chapterId)
                .eq(EduReview::getRefType, ReviewType.CHAPTER)
                .orderByDesc(EduReview::getId);
        List<EduReview> reviewList = reviewService.list(wrapper);
        return Result.success().data(reviewList);
    }
    //end chapter

    /**
     * create review request by chapter id
     * @param sectionId the id of chapter
     * @param reviewVo the info of lab member who requested the review
     * @return success() when success
     */
    @PostMapping("section/{sectionId}")
    @PreAuthorize("hasAuthority('course.review.request')")
    public Result requestReviewBySectionId(@PathVariable Long sectionId,
                                           @RequestBody ReviewApplyVo reviewVo){
        reviewService.requestReviewBySectionId(sectionId,reviewVo);
        return Result.success() ;
    }

    /**
     * pass the review and set the review status to finished.
     * @param sectionId the id of section
     * @param reviewVo the info of lab member who request the review
     * @return success() when success
     */
    @PutMapping("section/{sectionId}")
    @PreAuthorize("hasAuthority('course.review.pass')")
    public Result passReviewBySectionId(@PathVariable Long sectionId,
                                        @RequestBody ReviewApplyVo reviewVo){
        reviewService.passReviewBySectionId(sectionId,reviewVo);
        return Result.success() ;
    }

    /**
     /**
     * reject the review and set the review status to finished.
     * @param sectionId the id of section
     * @param reviewVo the info of lab member who request the review
     * @return success() when success
     */
    @PostMapping("section/reject/{sectionId}")
    @PreAuthorize("hasAuthority('course.review.reject')")
    public Result rejectReviewBySectionId(@PathVariable Long sectionId,
                                          @RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewBySectionId(sectionId,reviewVo);
        return Result.success() ;
    }

    /**
     * get review history of the section
     * @param sectionId the id of section
     * @return Review list of the section
     */
    @GetMapping("section/{sectionId}")
    @PreAuthorize("hasAuthority('course.review')")
    public Result getReviewListBySectionId(@PathVariable Long sectionId){
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefId,sectionId)
                .eq(EduReview::getRefType, ReviewType.SECTION)
                .orderByDesc(EduReview::getId);
        List<EduReview> reviewList = reviewService.list(wrapper);
        return Result.success().data(reviewList);
    }

    // review operations base on course ID
    // BEGIN

    @PostMapping("{courseId}")
    @PreAuthorize("hasAuthority('course.review.request')")
    public Result requestReviewByCourseId(@PathVariable Long courseId, @RequestBody ReviewApplyVo reviewVo){
        reviewService.requestReviewByCourseId(courseId, reviewVo);
        return Result.success();
    }

    /**
     * pass all reviews which has the same course id
     * @param courseId the id of course
     * @param reviewVo the info of lab member and review message
     * @return success() when success
     */
    @PutMapping("{courseId}")
    @PreAuthorize("hasAuthority('course.review.pass')")
    public Result passReviewByCourseId(@PathVariable Long courseId,
                                       @RequestBody ReviewApplyVo reviewVo){
        reviewService.passReviewByCourseId(courseId, reviewVo);
        return Result.success();
    }
    @PostMapping("reject/{courseId}")
    @PreAuthorize("hasAuthority('course.review.reject')")
    public Result rejectReviewByCourseId(@PathVariable Long courseId,
                                         @RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByCourseId(courseId, reviewVo);
        return Result.success();
    }
    // END

    // review for research
    // review for research in EduResearchController
    // review for methodology in EduMethodologyController
}
