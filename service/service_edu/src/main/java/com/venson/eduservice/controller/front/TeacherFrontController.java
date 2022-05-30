package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.QueryResult;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.EduTeacher;
import com.venson.eduservice.service.EduCourseService;
import com.venson.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public RMessage getTeacherFrontList(@PathVariable Integer page, @PathVariable Integer limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        Map<String, Object> map = eduTeacherService.getTeacherFrontList(teacherPage);
        return RMessage.ok().data(map);
    }

    @GetMapping("member/{id}")
    public RMessage getMemberFrontById(@PathVariable String id){
        EduTeacher member = eduTeacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = eduCourseService.list(wrapper);


        return RMessage.ok().data("member", member).data("list", courseList);
    }

}
