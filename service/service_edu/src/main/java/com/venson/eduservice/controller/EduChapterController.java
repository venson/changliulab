package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.chapter.ChapterVo;
import com.venson.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;


    @GetMapping("getChapterVideo/{courseId}")
    public RMessage getChapterVideo( @PathVariable("courseId") String courseId ){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return RMessage.ok().data("list",list);
    }

    @PostMapping("addChapter")
    public RMessage addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
       return RMessage.ok();
    }

    @GetMapping("getChapter/{chapterId}")
    public RMessage getChapter(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return RMessage.ok().data("item", eduChapter);
    }

    @PostMapping("updateChapter")
    public RMessage updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return RMessage.ok();
    }

    @DeleteMapping("{chapterId}")
    public RMessage deleteChapter(@PathVariable String chapterId){
        Boolean result = eduChapterService.deleteChapter(chapterId);
        return result? RMessage.ok(): RMessage.error();
    }
}
