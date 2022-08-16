package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.EduChapterMarkdown;
import com.venson.eduservice.entity.vo.ChapterVo;
import com.venson.eduservice.service.*;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/eduservice/edu-chapter")
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
    public RMessage getChapterById(@PathVariable Long chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        EduChapterMarkdown chapterMd = markdownService.getById(chapterId);
        ChapterVo chapterVo = new ChapterVo();
        BeanUtils.copyProperties(chapter, chapterVo);
        chapterVo.setMarkdown(chapterMd);
        return RMessage.ok().data(chapterVo);
    }
    @Transactional
    @PostMapping("")
    public RMessage addChapter(@RequestBody ChapterVo chapterVo){
        if(!ObjectUtils.isEmpty(chapterVo.getId())){
            throw new CustomizedException(40000,"chapter already exist");
        }
        EduChapter chapter = new EduChapter();
        EduChapterMarkdown chapterMarkdown = new EduChapterMarkdown();
        BeanUtils.copyProperties(chapterVo,chapter);
        chapterService.save(chapter);
        chapterMarkdown.setId(chapter.getId());
        chapterMarkdown.setMarkdown(chapterVo.getMarkdown().getMarkdown());
        markdownService.save(chapterMarkdown);
        return RMessage.ok().data("id",chapter.getId());
    }

    @Transactional
    @PutMapping("{chapterId}")
    public RMessage updateChapterById(@PathVariable Long chapterId, @RequestBody ChapterVo chapter){
        chapterService.updateChapterById(chapterId,chapter);

        return RMessage.ok();
    }

    @Deprecated
    @PutMapping("md/{chapterId}")
    public RMessage updateChapterMdById(@PathVariable Long chapterId, @RequestBody EduChapterMarkdown markdown){
        boolean success = markdownService.updateById(markdown);
        return success? RMessage.ok() : RMessage.error();
    }



    @Transactional
    @DeleteMapping("{chapterId}")
    public RMessage deleteChapterById( @PathVariable Long chapterId ){
        // mark chapter and section, deletion will be performed after review.
        chapterService.removeChapterById(chapterId);
        return RMessage.error();

    }

}
