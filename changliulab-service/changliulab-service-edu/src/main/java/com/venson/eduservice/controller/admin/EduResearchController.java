package com.venson.eduservice.controller.admin;

import com.venson.commonutils.PageResponse;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.dto.ResearchDTO;
import com.venson.eduservice.entity.enums.LanguageEnum;
import com.venson.eduservice.service.EduResearchService;
import com.venson.eduservice.service.EduReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('research.list')")
    public Result<PageResponse<EduResearch>> getResearchPage(@PathVariable Integer page, @PathVariable Integer limit){
        PageResponse<EduResearch> pageRes = service.getResearchPage(page, limit);
        return Result.success(pageRes);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('research.edit')")
    public Result<EduResearch> getResearch(@PathVariable Long id){
        EduResearch research = service.getById(id);
        return Result.optional(research);
    }
    @GetMapping("preview/{id}")
    public Result<ResearchDTO> getResearchPreviewById(@PathVariable Long id){
        ResearchDTO preview = service.getResearchPreviewById(id);
        return Result.success(preview);
    }
    @PostMapping()
    public Result<Long> addResearch(@Valid @RequestBody ResearchDTO research){
        Long id = service.addResearch(research);
        return Result.success(id);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('research.edit')")
    public Result<String> updateResearch(@PathVariable Long id,@Valid @RequestBody ResearchDTO research){
        service.updateResearch(id,research);
        return Result.success();
    }
    @DeleteMapping("{id}")
    public Result<String> removeResearch(@PathVariable Long id){
        service.removeResearchById(id);
        return Result.success();
    }

    @GetMapping("review/{id}")
    @PreAuthorize("hasAnyAuthority('research.review', 'research.review.request','research.review.pass','research.review.reject')")
    public Result<List<EduReview>> getReview(@PathVariable Long id){
        List<EduReview> reviewList = reviewService.getReviewByResearchId(id);
        return Result.success(reviewList);
    }
    @GetMapping("review/{current}/{size}")
    public Result<PageResponse<EduResearch>> getResearchReviewPage(@PathVariable Integer current, @PathVariable Integer size){
        PageResponse<EduResearch> page =service.getResearchReviewPage(current, size);
        return Result.success(page);
    }
    @GetMapping(value = "enable/{id}",params = {"lang"})
    public Result<String> switchEnableById(@PathVariable Long id, @RequestParam LanguageEnum lang){
        service.switchEnableById(id, lang);
        return Result.success();
    }

}
