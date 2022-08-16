package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.vo.ReviewApplyVo;
import com.venson.eduservice.service.EduResearchService;
import com.venson.eduservice.service.EduReviewService;
import kotlin.jvm.internal.Lambda;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/edu-research")
public class EduResearchController {

    @Autowired
    private EduResearchService service;
    @Autowired
    private EduReviewService reviewService;

    @GetMapping()
    public RMessage getResearch(){
        LambdaQueryWrapper<EduResearch> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduResearch::getId, EduResearch::getStatus, EduResearch::getIsModified
                        ,EduResearch::getMarkdown,EduResearch::getLanguage, EduResearch::getIsPublished);
        List<EduResearch> list = service.list();
        return RMessage.ok().data(list);
    }
    @GetMapping("{id}")
    public RMessage getResearch(@PathVariable Long id){
        LambdaQueryWrapper<EduResearch> wrapper = new QueryWrapper<EduResearch>().lambda();
        wrapper.eq(EduResearch::getId, id);
        wrapper.select(EduResearch::getId, EduResearch::getStatus, EduResearch::getIsModified
                ,EduResearch::getMarkdown,EduResearch::getLanguage, EduResearch::getIsPublished);
        EduResearch research = service.getOne(wrapper);
        return RMessage.ok().data(research);
    }

    @GetMapping("review/{id}")
    public RMessage getReviewList(@PathVariable Long id){
        List<EduReview> reviewList = reviewService.getReviewByResearchId(id);
        return RMessage.ok().data(reviewList);
    }
    @PutMapping("{id}")
    public RMessage updateResearch(@PathVariable Long id, @RequestBody EduResearch research){
        EduResearch eduResearch= service.getById(id);
        if(eduResearch.getStatus() == ReviewStatus.APPLIED){
            return RMessage.error().message("The Research is under review");
        }
        research.setIsModified(true);
        service.updateById(research);
        return RMessage.ok();
    }

    @PostMapping("review/{id}")
    public RMessage requestReview(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.requestReviewByResearchId(id, applyVo);
        return RMessage.ok();
    }
    @PutMapping("review/{id}")
    public RMessage passReviewByResearchId(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.passReviewByResearchId(id,applyVo);
        return RMessage.ok();
    }

    @PostMapping("review/reject/{id}")
    public RMessage rejectReview(@PathVariable Long id,@RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByResearchId(id, reviewVo);
        return RMessage.ok();
    }
    @GetMapping("review")
    public RMessage getResearchReviewList(){
        List<EduResearch> list = service.getResearchReviewList();
        return RMessage.ok().data(list);
    }
}
