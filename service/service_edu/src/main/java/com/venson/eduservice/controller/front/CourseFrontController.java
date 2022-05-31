package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.frontvo.CourseFrontVo;
import com.venson.eduservice.service.EduCourseService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/coursefront")
@Slf4j
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("course/{page}/{limit}")
    public RMessage getFrontPageCourseList(@PathVariable Integer page,
                                           @PathVariable Integer limit,
                                           @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        log.info(page+ "/" + limit);

        Map<String, Object> map = eduCourseService.getFrontPageCourseList(pageCourse,courseFrontVo);


        return RMessage.ok().data(map);
    }
}
