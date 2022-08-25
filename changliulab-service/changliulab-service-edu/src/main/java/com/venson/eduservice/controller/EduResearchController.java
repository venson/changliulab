package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
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
    public RMessage getResearch(){
        LambdaQueryWrapper<EduResearch> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduResearch::getId, EduResearch::getStatus, EduResearch::getIsModified
                        ,EduResearch::getMarkdown,EduResearch::getLanguage, EduResearch::getIsPublished);
        List<EduResearch> list = service.list();
        return RMessage.ok().data(list);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('research.edit')")
    public RMessage getResearch(@PathVariable Long id){
        LambdaQueryWrapper<EduResearch> wrapper = new QueryWrapper<EduResearch>().lambda();
        wrapper.eq(EduResearch::getId, id);
        wrapper.select(EduResearch::getId, EduResearch::getStatus, EduResearch::getIsModified
                ,EduResearch::getMarkdown,EduResearch::getLanguage, EduResearch::getIsPublished
        ,EduResearch::getIsModified);
        EduResearch research = service.getOne(wrapper);
        return RMessage.ok().data(research);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('research.edit')")
    public RMessage updateResearch(@PathVariable Long id, @RequestBody EduResearch research){
        EduResearch eduResearch= service.getById(id);
        if(eduResearch.getStatus() == ReviewStatus.APPLIED){
            return RMessage.error().message("The Research is under review");
        }
        research.setIsModified(true);
        service.updateById(research);
        return RMessage.ok();
    }

    @GetMapping("review/{id}")
    @PreAuthorize("hasAnyAuthority('research.review', 'research.review.request','research.review.pass','research.review.reject')")
    public RMessage getReviewList(@PathVariable Long id){
        List<EduReview> reviewList = reviewService.getReviewByResearchId(id);
        return RMessage.ok().data(reviewList);
    }
    @PostMapping("review/{id}")
    @PreAuthorize("hasAuthority('research.review.request')")
    public RMessage requestReview(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.requestReviewByResearchId(id, applyVo);
        return RMessage.ok();
    }
    @PutMapping("review/{id}")
    @PreAuthorize("hasAuthority('research.review.pass')")
    public RMessage passReviewByResearchId(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.passReviewByResearchId(id,applyVo);
        return RMessage.ok();
    }

    @PostMapping("review/reject/{id}")
    @PreAuthorize("hasAuthority('research.review.reject')")
    public RMessage rejectReview(@PathVariable Long id,@RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByResearchId(id, reviewVo);
        return RMessage.ok();
    }
    @GetMapping("review")
    @PreAuthorize("hasAuthority('research.review')")
    public RMessage getResearchReviewList(){
        List<EduResearch> list = service.getResearchReviewList();
        return RMessage.ok().data(list);
    }
}
