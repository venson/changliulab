package com.venson.eduservice.controller.front;

import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.service.*;
import com.venson.eduservice.service.front.CourseFrontService;
import com.venson.eduservice.service.front.ScholarFrontService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/front/member")
//@CrossOrigin
@Slf4j
public class MemberFrontController {

    @Autowired
    private EduMemberService eduMemberService;
    @Autowired
    private EduCoursePublishedService eduCoursePublishedService;
    @Autowired
    private ScholarFrontService scholarFrontService;

    @Autowired
    private CourseFrontService courseFrontService;

    @GetMapping("{page}/{limit}")
    public Result getMemberFrontList(@PathVariable Integer page, @PathVariable Integer limit,
                                     @RequestParam(required = false) String level){
        Map<String,Object> map = eduMemberService.getPageFrontMemberList(page,limit,level);
        return Result.success().data(map);
    }

    @GetMapping("{id}/{page}/{limit}")
    public Result getMemberFrontById(@PathVariable Long id,@PathVariable Integer page, @PathVariable Integer limit){
        EduMember member = eduMemberService.getById(id);
        Map<String, Object> coursePageMap = courseFrontService.getPageCourseByMemberId(id,page,limit);
        Map<String, Object> scholarPageMap =scholarFrontService.getPageScholarByMemberId(id, page, limit);

        return Result.success().data("member", member)
                .data("course",coursePageMap)
                .data("scholar",scholarPageMap);
    }

}
