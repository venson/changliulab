package com.venson.eduservice.controller.admin;

import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduChapterMarkdown;
import com.venson.eduservice.entity.dto.ChapterDTO;
import com.venson.eduservice.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
    public Result getChapterById(@PathVariable Long chapterId){
        ChapterDTO chapterDTO = chapterService.getChapterDTOById(chapterId);
        return Result.success().data(chapterDTO);
    }
    @PostMapping("")
    @PreAuthorize("hasAuthority('course.edit.content')")
    @Transactional(rollbackFor = Exception.class)
    public Result addChapter(@RequestBody ChapterDTO chapterDTO){
        return chapterService.addChapter(chapterDTO);
    }

    @PostMapping(value = "",params = {"courseId"})
    @PreAuthorize("hasAuthority('course.edit.content')")
    @Transactional(rollbackFor = Exception.class)
    public Result addEmptyChapter(@RequestParam Long courseId){
        Long id =  chapterService.addEmptyChapter(courseId);
        return Result.success("id",id);
    }
    @PutMapping("{chapterId}")
    @PreAuthorize("hasAuthority('course.edit.content')")
    public Result updateChapterById(@PathVariable Long chapterId, @RequestBody ChapterDTO chapter){
        chapterService.updateChapterById(chapterId,chapter);
        return Result.success();
    }

    @Deprecated
    @PutMapping("md/{chapterId}")
    @PreAuthorize("hasAuthority('course.edit.content')")
    public Result updateChapterMdById(@PathVariable Long chapterId, @RequestBody EduChapterMarkdown markdown){
        boolean success = markdownService.updateById(markdown);
        return success? Result.success() : Result.error();
    }


    /**
     * mark the chapter, will remove after review
     */
    @DeleteMapping("{chapterId}")
    @PreAuthorize("hasAuthority('course.edit.remove')")
    public Result deleteChapterById(@PathVariable Long chapterId ){
        return chapterService.removeChapterById(chapterId);

    }

}
