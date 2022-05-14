package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.vo.CourseInfoVo;
import com.venson.eduservice.entity.vo.CoursePublishVo;
import com.venson.eduservice.service.EduCourseService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
@Slf4j
public class EduCourseController {

    private final EduCourseService eduCourseService;

    public EduCourseController(EduCourseService eduCourseService) {
        this.eduCourseService = eduCourseService;
    }

    @PostMapping("addCourseInfo")
    public RMessage addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return RMessage.ok().data("courseId", id);
    }


    @GetMapping("getCourseInfo/{courseId}")
    public RMessage getCourseInfo(@PathVariable("courseId") String id){
        CourseInfoVo infoVo = eduCourseService.getCourseInfo(id);
        return  RMessage.ok().data("course", infoVo);
    }

    @PostMapping("updateCourseInfo")
    public RMessage updateCourseInfo(@RequestBody CourseInfoVo infoVo){
        eduCourseService.updateCourseInfo(infoVo);
        return RMessage.ok();
    }

    @GetMapping("publishCourseInfo/{id}")
    public RMessage getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourseInfoById(id);
        return RMessage.ok().data("item", coursePublishVo);
    }


    @PostMapping("publishCourse/{id}")
    public RMessage publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return RMessage.ok();
    }


    @PostMapping("{pageNum}/{limit}")
    public RMessage courseList(@PathVariable Integer pageNum,
                               @PathVariable Integer limit,
                               @RequestBody(required = false) String condition){
        Page<CoursePublishVo> page = new Page<>(pageNum, limit);
        QueryWrapper<CoursePublishVo> wrapper = new QueryWrapper<>();
        if (condition != null && !condition.isEmpty()){
            wrapper.like("c.title",condition).or()
            .like("s.title",condition).or()
            .like("s2.title",condition).or()
            .like("t.name",condition).or()
            .like("cd.description",condition);
        }
        eduCourseService.selectPageVo(page, wrapper);
        List<CoursePublishVo> records = page.getRecords();
        long total = page.getTotal();


        return RMessage.ok().data("total",total).data("row", records);
    }


    @DeleteMapping("{courseId}")
    public RMessage removeCourseById(@PathVariable String courseId){
        eduCourseService.removeCourseById(courseId) ;
        return RMessage.ok();
    }
}
