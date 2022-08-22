package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.EduChapterMarkdown;
import com.venson.eduservice.entity.dto.ChapterDTO;
import com.venson.eduservice.service.*;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/eduservice/admin/edu-chapter")
public class EduChapterController {

    private final EduChapterService chapterService;
    private final EduChapterMarkdownService markdownService;

    public EduChapterController(EduChapterService chapterService,
                                EduChapterMarkdownService markdownService) {
        this.chapterService = chapterService;
        this.markdownService = markdownService;
    }

    /**
     * get chapter and markdown by chapter ID
     * @param chapterId the id of chapter
     * @return chapter and markdown
     */
    @GetMapping("{chapterId}")
    @PreAuthorize("hasAnyAuthority('course.edit.content', 'course.edit.preview')")
    public RMessage getChapterById(@PathVariable Long chapterId){
        return chapterService.getChapterDTOById(chapterId);
    }
    @PostMapping("")
    @PreAuthorize("hasAuthority('course.edit.content')")
    public RMessage addChapter(@RequestBody ChapterDTO chapterDTO){
        return chapterService.addChapter(chapterDTO);
    }

    @PutMapping("{chapterId}")
    @PreAuthorize("hasAuthority('course.edit.content')")
    public RMessage updateChapterById(@PathVariable Long chapterId, @RequestBody ChapterDTO chapter){
        chapterService.updateChapterById(chapterId,chapter);

        return RMessage.ok();
    }

    @Deprecated
    @PutMapping("md/{chapterId}")
    @PreAuthorize("hasAuthority('course.edit.content')")
    public RMessage updateChapterMdById(@PathVariable Long chapterId, @RequestBody EduChapterMarkdown markdown){
        boolean success = markdownService.updateById(markdown);
        return success? RMessage.ok() : RMessage.error();
    }


    /**
     * mark the chapter, will remove after review
     */
    @DeleteMapping("{chapterId}")
    @PreAuthorize("hasAuthority('course.edit.remove')")
    public RMessage deleteChapterById( @PathVariable Long chapterId ){
        return chapterService.removeChapterById(chapterId);

    }

}
