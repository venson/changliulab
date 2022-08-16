package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.entity.vo.ReviewApplyVo;
import com.venson.eduservice.service.EduMethodologyService;
import com.venson.eduservice.service.EduResearchService;
import com.venson.eduservice.service.EduReviewService;
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
@RequestMapping("/eduservice/edu-methodology")
public class EduMethodologyController {
    @Autowired
    private EduMethodologyService service;
    @Autowired
    private EduReviewService reviewService;

    @GetMapping()
    public RMessage getMethodology(){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.orderByAsc(EduMethodology::getGmtCreate);
        List<EduMethodology> list = service.list(wrapper);
        return RMessage.ok().data("item", list);
    }
    @GetMapping("{id}")
    public RMessage getMethodology(@PathVariable Long id){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.eq(EduMethodology::getId, id);
        wrapper.select(EduMethodology::getMarkdown, EduMethodology::getId, EduMethodology::getLanguage);
        EduMethodology methodology = service.getOne(wrapper);
        return RMessage.ok().data("item",methodology);
    }

    @PutMapping("{id}")
    public RMessage updateMethodology(@PathVariable Long id, @RequestBody EduMethodology methodology){
        EduMethodology eduMethodology= service.getById(id);
//        if(eduMethodology.getPublishRequest()){
//            return RMessage.error().message("The Methodology is under request can not be edit");
//        }

        methodology.setIsModified(true);
        service.updateById(methodology);
        return RMessage.ok();
    }

    @PostMapping("review/{id}")
    public RMessage requestReview(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.requestReviewByMethodologyId(id, applyVo);
        return RMessage.ok();
    }
    @PutMapping("review/{id}")
    public RMessage passReviewByMethodologyId(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.passReviewByMethodologyId(id,applyVo);
        return RMessage.ok();
    }

    @PutMapping("review/reject/{id}")
    public RMessage rejectReview(@PathVariable Long id,@RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByMethodologyId(id, reviewVo);
        return RMessage.ok();
    }
}
