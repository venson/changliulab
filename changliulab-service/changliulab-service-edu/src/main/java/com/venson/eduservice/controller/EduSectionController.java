package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduSectionMarkdown;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.enums.ReviewType;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.entity.dto.SectionDTO;
import com.venson.eduservice.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/eduservice/admin/edu-section")
@Slf4j
public class EduSectionController {



    private final EduSectionService sectionService;
    private final EduSectionMarkdownService markdownService;
    private final EduReviewService reviewService;

    public EduSectionController(EduSectionMarkdownService markdownService,
                                EduReviewService reviewService,
                                EduSectionService sectionService) {
        this.markdownService = markdownService;
        this.reviewService = reviewService;
        this.sectionService = sectionService;
    }

    /**
     * get section and markdown by section ID
     * @param sectionId the id of section
     * @return section and markdown
     */
    @GetMapping("{sectionId}")
    @PreAuthorize("hasAnyAuthority('course.edit.content', 'course.edit.preview')")
    public RMessage getSectionById(@PathVariable Long sectionId){
        SectionDTO section = sectionService.getSectionById(sectionId);
        return RMessage.ok().data(section);
    }
    @Transactional
    @PostMapping("")
    @PreAuthorize("hasAuthority('course.edit.content')")
    public RMessage addSectionById(@RequestBody SectionDTO section){
        Long id = sectionService.addSection(section);
        return RMessage.ok().data("id",id);
    }

    @PutMapping("{sectionId}")
    @PreAuthorize("hasAuthority('course.edit.content')")
    public RMessage updateSectionById(@PathVariable Long sectionId, @RequestBody SectionDTO sectionDTO){
        sectionService.updateSectionById(sectionId, sectionDTO);
        return RMessage.ok();
    }

    @Deprecated
    @PutMapping("md/{sectionId}")
    @PreAuthorize("hasAuthority('course.edit.content')")
    public RMessage updateSectionMdById(@PathVariable Long sectionId, @RequestBody EduSectionMarkdown markdown){
        boolean success = markdownService.updateById(markdown);
        return success? RMessage.ok() : RMessage.error();
    }

    @PostMapping("review/{sectionId}")
    @PreAuthorize("hasAuthority('course.review.request')")
    public RMessage reviewRequestBySectionId(@PathVariable Long sectionId,
                                             @RequestBody ReviewApplyVo reviewVo){

        reviewService.requestReviewBySectionId(sectionId, reviewVo);
        return RMessage.ok() ;
    }

    @GetMapping("review/{sectionId}")
    @PreAuthorize("hasAnyAuthority('course.review.request', 'course.review.pass', 'course.review.reject')")
    public RMessage getReviewListBySectionId(@PathVariable Long sectionId){
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefId,sectionId)
                .eq(EduReview::getRefType,ReviewType.SECTION)
                .orderByDesc(EduReview::getId);
        List<EduReview> reviewList = reviewService.list(wrapper);
        return RMessage.ok().data(reviewList);
    }



    @Transactional
    @DeleteMapping("{sectionId}")
    public RMessage deleteSectionById( @PathVariable Long sectionId ){
        sectionService.removeSectionById(sectionId);
        sectionService.removeById(sectionId);
        return RMessage.error();

    }
}