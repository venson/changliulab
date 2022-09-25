package com.venson.eduservice.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.service.EduMethodologyService;
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
@RequestMapping("/eduservice/admin/edu-methodology")
public class EduMethodologyController {
    @Autowired
    private EduMethodologyService service;
    @Autowired
    private EduReviewService reviewService;

    @GetMapping()
    @PreAuthorize("hasAuthority('methodology.list')")
    public Result getMethodology(){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.orderByAsc(EduMethodology::getGmtCreate);
        List<EduMethodology> list = service.list(wrapper);
        return Result.success().data("item", list);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('methodology.edit')")
    public Result getMethodology(@PathVariable Long id){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.eq(EduMethodology::getId, id);
        wrapper.select(EduMethodology::getMarkdown, EduMethodology::getId
                ,EduMethodology::getIsModified
                , EduMethodology::getStatus,EduMethodology::getIsPublished
                ,EduMethodology::getLanguage);
        EduMethodology methodology = service.getOne(wrapper);
        return Result.success().data(methodology);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('methodology.edit')")
    public Result updateMethodology(@PathVariable Long id, @RequestBody EduMethodology methodology){
        EduMethodology eduMethodology= service.getById(id);
//        if(eduMethodology.getPublishRequest()){
//            return RMessage.error().message("The Methodology is under request can not be edit");
//        }

        methodology.setIsModified(true);
        service.updateById(methodology);
        return Result.success();
    }

    @GetMapping("review/{id}")
    @PreAuthorize("hasAnyAuthority('methodology.review', 'methodology.review.request','methodology.review.pass','methodology.review.reject')")
    public Result getReviewList(@PathVariable Long id){
        List<EduReview> reviewList = reviewService.getReviewByMethodologyId(id);
        return Result.success().data(reviewList);
    }
    @PostMapping("review/{id}")
    @PreAuthorize("hasAuthority('methodology.review.request')")
    public Result requestReview(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.requestReviewByMethodologyId(id, applyVo);
        return Result.success();
    }
    @PutMapping("review/{id}")
    @PreAuthorize("hasAuthority('methodology.review.pass')")
    public Result passReviewByMethodologyId(@PathVariable Long id, @RequestBody ReviewApplyVo applyVo){
        reviewService.passReviewByMethodologyId(id,applyVo);
        return Result.success();
    }

    @PutMapping("review/reject/{id}")
    @PreAuthorize("hasAuthority('methodology.review.reject')")
    public Result rejectReview(@PathVariable Long id, @RequestBody ReviewApplyVo reviewVo){
        reviewService.rejectReviewByMethodologyId(id, reviewVo);
        return Result.success();
    }
    @GetMapping("review")
    @PreAuthorize("hasAuthority('methodology.review')")
    public Result getMethodologyReviewList(){
        List<EduMethodology> list = service.getMethodologyReviewList();
        return Result.success().data(list);
    }
}
