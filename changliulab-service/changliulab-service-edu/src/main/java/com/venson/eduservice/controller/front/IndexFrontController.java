package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduActivityPublished;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.service.EduActivityPublishedService;
import com.venson.eduservice.service.EduCoursePublishedService;
import com.venson.eduservice.service.EduMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for front main page
 */
@RestController
@RequestMapping("eduservice/front/index")
public class IndexFrontController {
    @Autowired
    private EduCoursePublishedService coursePublishedService;
    @Autowired
    private EduActivityPublishedService activityPublishedService;

    @Autowired
    private EduMemberService eduMemberService;

    @GetMapping()
    public Result index(){
        LambdaQueryWrapper<EduCoursePublished> courseWrapper = new LambdaQueryWrapper<>();
        courseWrapper.orderByDesc(EduCoursePublished::getViewCount)
                .last("limit 8");
        List<EduCoursePublished> courseList = coursePublishedService.list(courseWrapper);


        LambdaQueryWrapper<EduMember> memberWrapper= new LambdaQueryWrapper<>();
        memberWrapper.eq(EduMember::getLevel,1).orderByDesc(EduMember::getId).last("limit 4");
        List<EduMember> memberList= eduMemberService.list(memberWrapper);

        LambdaQueryWrapper<EduActivityPublished> activityWrapper = new LambdaQueryWrapper<>();
        activityWrapper.orderByDesc(EduActivityPublished::getId);
        List<EduActivityPublished> activityList = activityPublishedService.list(activityWrapper);

        return Result.success().data("member",memberList).data("course", courseList).data("activity", activityList);
    }
}
