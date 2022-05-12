package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.chapter.ChapterVo;
import com.venson.eduservice.service.EduChapterService;
import com.venson.eduservice.service.EduCourseService;
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

}
