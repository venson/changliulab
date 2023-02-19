package com.venson.eduservice.controller.admin;

import com.venson.commonutils.PageResponse;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.dto.MethodologyDTO;
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

    @GetMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('methodology.list')")
    public Result<PageResponse<EduMethodology>> getMethodologyPage(@PathVariable Integer page, @PathVariable Integer limit){
        PageResponse<EduMethodology> pageRes = service.getMethodologyPage(page, limit);
        return Result.success(pageRes);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('methodology.edit')")
    public Result<EduMethodology> getMethodology(@PathVariable Long id){
        EduMethodology methodology = service.getById(id);
        return Result.optional(methodology);
    }
    @GetMapping("view/{id}")
    public Result<MethodologyDTO> getMethodologyViewById(@PathVariable Long id){
        MethodologyDTO view = service.getMethodologyViewById(id);
        return Result.success(view);
    }
    @PostMapping()
    public Result<String> addMethodology(@RequestBody MethodologyDTO methodology){
        service.addMethodology(methodology);
        return Result.success();
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('methodology.edit')")
    public Result<String> updateMethodology(@PathVariable Long id, @RequestBody MethodologyDTO methodology){
        service.updateMethodology(id,methodology);
        return Result.success();
    }

    @GetMapping("review/{id}")
    @PreAuthorize("hasAnyAuthority('methodology.review', 'methodology.review.request','methodology.review.pass','methodology.review.reject')")
    public Result<List<EduReview>> getReview(@PathVariable Long id){
        List<EduReview> reviewList = reviewService.getReviewByMethodologyId(id);
        return Result.success(reviewList);
    }
    @GetMapping("review/{current}/{size}")
    public Result<PageResponse<EduMethodology>> getMethodologyReviewPage(@PathVariable Integer current, @PathVariable Integer size){
        PageResponse<EduMethodology> page =service.getMethodologyReviewPage(current, size);
        return Result.success(page);
    }



}
