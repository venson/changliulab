package com.venson.eduservice.controller.front;

import com.venson.commonutils.Result;
import com.venson.eduservice.entity.front.dto.ActivityFrontBriefDTO;
import com.venson.eduservice.entity.front.dto.CourseFrontBriefDTO;
import com.venson.eduservice.entity.front.dto.IndexFrontDTO;
import com.venson.eduservice.entity.front.dto.MemberFrontBriefDTO;
import com.venson.eduservice.service.front.ActivityFrontService;
import com.venson.eduservice.service.front.CourseFrontService;
import com.venson.eduservice.service.front.MemberFrontService;
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
    private CourseFrontService courseFrontService;

    @Autowired
    private ActivityFrontService  activityFrontService;

    @Autowired
    private MemberFrontService memberFrontService;


    @GetMapping()
    public Result<IndexFrontDTO> index(){
        List<CourseFrontBriefDTO> courseList = courseFrontService.getFrontIndexCourse();
        List<ActivityFrontBriefDTO> activityList = activityFrontService.getFrontIndexActivity();
        List<MemberFrontBriefDTO> memberList = memberFrontService.getFrontIndexMember();



        IndexFrontDTO index = new IndexFrontDTO();
        index.setCourse(courseList);
        index.setActivity(activityList);
        index.setMember(memberList);
        return Result.success(index);
    }
}
