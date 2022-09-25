package com.venson.eduservice.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.service.EduResearchService;
import com.venson.eduservice.service.EduReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/eduservice/admin/edu-research")
public class EduResearchController {

    @Autowired
    private EduResearchService service;
    @Autowired
    private EduReviewService reviewService;

    @GetMapping()
    @PreAuthorize("hasAuthority('research.list')")
    public Result getResearch(){
        LambdaQueryWrapper<EduResearch> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduResearch::getId, EduResearch::getStatus, EduResearch::getIsModified
                        ,EduResearch::getMarkdown,EduResearch::getLanguage, EduResearch::getIsPublished);
        List<EduResearch> list = service.list();
        return Result.success().data(list);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('research.edit')")
    public Result getResearch(@PathVariable Long id){
        LambdaQueryWrapper<EduResearch> wrapper = new QueryWrapper<EduResearch>().lambda();
        wrapper.eq(EduResearch::getId, id);
        wrapper.select(EduResearch::getId, EduResearch::getStatus, EduResearch::getIsModified
                ,EduResearch::getMarkdown,EduResearch::getLanguage, EduResearch::getIsPublished
        ,EduResearch::getIsModified);
        EduResearch research = service.getOne(wrapper);
        return Result.success().data(research);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('research.edit')")
    public Result updateResearch(@PathVariable Long id, @RequestBody EduResearch research){
        EduResearch eduResearch= service.getById(id);
        if(eduResearch.getStatus() == ReviewStatus.APPLIED){
            return Result.error().message("The Research is under review");
        }
        research.setIsModified(true);
        service.updateById(research);
        return Result.success();
    }

    @GetMapping("review/{id}")
    @PreAuthorize("hasAnyAuthority('research.review', 'research.review.request','research.review.pass','research.review.reject')")
    public Result getReviewList(@PathVariable Long id){
        List<EduReview> reviewList = reviewService.getReviewByResearchId(id);
        return Result.success().data(reviewList);
    }
    @PostMapping("review/{id}")
    @PreAuthorize("hasAuthority('research.review.request')")
    public Result requestReview(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.requestReviewByResearchId(id, applyVo);
        return Result.success();
    }
    @PutMapping("review/{id}")
    @PreAuthorize("hasAuthority('research.review.pass')")
    public Result passReviewByResearchId(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.passReviewByResearchId(id,applyVo);
        return Result.success();
    }

    @PostMapping("review/reject/{id}")
    @PreAuthorize("hasAuthority('research.review.reject')")
    public Result rejectReview(@PathVariable Long id, @RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByResearchId(id, reviewVo);
        return Result.success();
    }
    @GetMapping("review")
    @PreAuthorize("hasAuthority('research.review')")
    public Result getResearchReviewList(){
        List<EduResearch> list = service.getResearchReviewList();
        return Result.success().data(list);
    }
}
