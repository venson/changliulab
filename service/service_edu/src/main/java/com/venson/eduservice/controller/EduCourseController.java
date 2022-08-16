package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.vo.CourseInfoVo;
import com.venson.eduservice.entity.vo.CoursePreviewVo;
import com.venson.eduservice.service.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@Slf4j
public class EduCourseController {

    private final EduCourseService eduCourseService;

    public EduCourseController(EduCourseService eduCourseService) {
        this.eduCourseService = eduCourseService;
    }

    @PostMapping("")
    public RMessage addCourse(@RequestBody CourseInfoVo courseInfoVo){
        Long id = eduCourseService.addCourse(courseInfoVo);
        return RMessage.ok().data("courseId", id);
    }


    @GetMapping("{courseId}")
    public RMessage getCourse(@PathVariable("courseId") Long id){
        CourseInfoVo infoVo = eduCourseService.getCourseById(id);
        return  RMessage.ok().data("course", infoVo);
    }

    @PutMapping("{courseId}")
    public RMessage updateCourse(@PathVariable Long courseId,@RequestBody CourseInfoVo infoVo){
        eduCourseService.updateCourse(infoVo);
        return RMessage.ok();
    }



    @PostMapping("{pageNum}/{limit}")
    public RMessage courseList(@PathVariable Integer pageNum,
                               @PathVariable Integer limit,
                               @RequestBody(required = false) String condition){

        Map<String,Object> map = eduCourseService.getPageCoursePublishVo(pageNum, limit, condition);
        return RMessage.ok().data(map);
    }


    @DeleteMapping("{courseId}")
    public RMessage removeCourseById(@PathVariable Long courseId){
        eduCourseService.removeCourseById(courseId) ;
        return RMessage.ok();
    }
    @GetMapping("preview/{courseId}")
    public RMessage getCoursePreviewById(@PathVariable Long courseId){
        CoursePreviewVo coursePreview = eduCourseService.getCoursePreviewById(courseId);
        return RMessage.ok().data(coursePreview);
    }

}
