package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.chapter.ChapterVo;
import com.venson.eduservice.entity.frontvo.CourseFrontFIlterVo;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.venson.eduservice.service.EduChapterService;
import com.venson.eduservice.service.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
@RequestMapping("/eduservice/coursefront")
@Slf4j
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @PostMapping("course/{page}/{limit}")
    public RMessage getFrontPageCourseList(@PathVariable Integer page,
                                           @PathVariable Integer limit,
                                           @RequestBody(required = false) CourseFrontFIlterVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        log.info(page+ "/" + limit);

        Map<String, Object> map = eduCourseService.getFrontPageCourseList(pageCourse,courseFrontVo);


        return RMessage.ok().data(map);
    }

    @GetMapping("course/{id}")
    public RMessage getFrontCourseInfo(@PathVariable String id){
        CourseFrontInfoVo courseFrontInfoVo = eduCourseService.getFrontCourseInfo(id);
        List<ChapterVo> chapterVo = eduChapterService.getChapterSectionByCourseId(id);
        return RMessage.ok().data("chapter",chapterVo).data("course",courseFrontInfoVo);
    }
}
