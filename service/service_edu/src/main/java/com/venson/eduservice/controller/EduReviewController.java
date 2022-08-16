package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.enums.ReviewType;
import com.venson.eduservice.entity.vo.ReviewApplyVo;
import com.venson.eduservice.service.*;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/eduservice/edu-review")
public class EduReviewController {

    private final EduReviewService reviewService;


    public EduReviewController(EduReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("{pageNum}/{limit}")
    public RMessage getPageCourseReviewList(@PathVariable Integer pageNum, @PathVariable Integer limit){
        Map<String,Object> map = reviewService.getPageReviewList(pageNum,limit);
        return RMessage.ok().data(map);

    }
    /**
     * create review request by chapter id
     * @param chapterId the id of chapter
     * @param reviewVo the info of lab member who requested the review
     * @return ok when success
     */
    @PostMapping("chapter/{chapterId}")
    public RMessage requestReviewByChapterId(@PathVariable Long chapterId,
                                             @RequestBody ReviewApplyVo reviewVo){
        reviewService.requestReviewByChapterId(chapterId,reviewVo);
        return RMessage.ok() ;
    }

    /**
     * pass the review and set the review status to finished.
     * @param chapterId the id of chapter
     * @param reviewVo the info of lab member who request the review
     * @return ok when success
     */
    @PutMapping("chapter/{chapterId}")
    public RMessage passReviewByChapterId(@PathVariable Long chapterId,
                                          @RequestBody ReviewApplyVo reviewVo){
        reviewService.passReviewByChapterId(chapterId,reviewVo);
        return RMessage.ok() ;
    }

    /**
     /**
     * reject the review and set the review status to finished.
     * @param chapterId the id of chapter
     * @param reviewVo the info of lab member who request the review
     * @return ok when success
     */
    @PostMapping("chapter/reject/{chapterId}")
    public RMessage rejectReviewByChapterId(@PathVariable Long chapterId,
                                            @RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByChapterId(chapterId,reviewVo);
        return RMessage.ok() ;
    }

    /**
     * get review history of the chapter
     * @param chapterId the id of chapter
     * @return Review list of the chapter
     */
    @GetMapping("chapter/{chapterId}")
    public RMessage getReviewListByChapterId(@PathVariable Long chapterId){
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefId,chapterId)
                .eq(EduReview::getRefType, ReviewType.CHAPTER)
                .orderByDesc(EduReview::getId);
        List<EduReview> reviewList = reviewService.list(wrapper);
        return RMessage.ok().data(reviewList);
    }
    //end chapter

    /**
     * create review request by chapter id
     * @param sectionId the id of chapter
     * @param reviewVo the info of lab member who requested the review
     * @return ok when success
     */
    @PostMapping("section/{sectionId}")
    public RMessage requestReviewBySectionId(@PathVariable Long sectionId,
                                             @RequestBody ReviewApplyVo reviewVo){
        reviewService.requestReviewBySectionId(sectionId,reviewVo);
        return RMessage.ok() ;
    }

    /**
     * pass the review and set the review status to finished.
     * @param sectionId the id of section
     * @param reviewVo the info of lab member who request the review
     * @return ok when success
     */
    @PutMapping("section/{sectionId}")
    public RMessage passReviewBySectionId(@PathVariable Long sectionId,
                                          @RequestBody ReviewApplyVo reviewVo){
        reviewService.passReviewBySectionId(sectionId,reviewVo);
        return RMessage.ok() ;
    }

    /**
     /**
     * reject the review and set the review status to finished.
     * @param sectionId the id of section
     * @param reviewVo the info of lab member who request the review
     * @return ok when success
     */
    @PostMapping("section/reject/{sectionId}")
    public RMessage rejectReviewBySectionId(@PathVariable Long sectionId,
                                            @RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewBySectionId(sectionId,reviewVo);
        return RMessage.ok() ;
    }

    /**
     * get review history of the section
     * @param sectionId the id of section
     * @return Review list of the section
     */
    @GetMapping("section/{sectionId}")
    public RMessage getReviewListBySectionId(@PathVariable Long sectionId){
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefId,sectionId)
                .eq(EduReview::getRefType, ReviewType.SECTION)
                .orderByDesc(EduReview::getId);
        List<EduReview> reviewList = reviewService.list(wrapper);
        return RMessage.ok().data(reviewList);
    }

    // review operations base on course ID
    // BEGIN

    @PostMapping("{courseId}")
    public RMessage requestReviewByCourseId(@PathVariable Long courseId,@RequestBody ReviewApplyVo reviewVo){
        reviewService.requestReviewByCourseId(courseId, reviewVo);
        return RMessage.ok();
    }

    /**
     * pass all reviews which has the same course id
     * @param courseId the id of course
     * @param reviewVo the info of lab member and review message
     * @return ok when success
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @PutMapping("{courseId}")
    public RMessage passReviewByCourseId(@PathVariable Long courseId,
                                           @RequestBody ReviewApplyVo reviewVo){
        reviewService.passReviewByCourseId(courseId, reviewVo);
        return RMessage.ok();
    }
    @Transactional
    @PostMapping("reject/{courseId}")
    public RMessage rejectReviewByCourseId(@PathVariable Long courseId,
                                           @RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByCourseId(courseId, reviewVo);
        return RMessage.ok();
    }
    // END

    // review for research
    // review for research in EduResearchController
    // review for methodology in EduMethodologyController
}
