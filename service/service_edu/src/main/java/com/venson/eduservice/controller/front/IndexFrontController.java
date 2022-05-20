package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.EduTeacher;
import com.venson.eduservice.service.EduCourseService;
import com.venson.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("eduservice/indexFront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService educourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("index")
    public RMessage index(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList = educourseService.list(wrapper);

        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(wrapperTeacher);

        return RMessage.ok().data("teacher",teacherList).data("course", courseList);
    }
}
