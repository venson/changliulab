package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.service.EduCourseService;
import com.venson.eduservice.service.EduMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/memberfront")
//@CrossOrigin
@Slf4j
public class MemberFrontController {

    @Autowired
    private EduMemberService eduMemberService;
    @Autowired
    private EduCourseService eduCourseService;
    @GetMapping("getMemberFrontList/{page}/{limit}")
    public RMessage getMemberFrontList(@PathVariable Integer page, @PathVariable Integer limit){
        Page<EduMember> memberPage = new Page<>(page,limit);
        Map<String, Object> map = eduMemberService.getMemberFrontList(memberPage);
        log.info(map.toString());
        return RMessage.ok().data(map);
    }

    @GetMapping("member/{id}")
    public RMessage getMemberFrontById(@PathVariable String id){
        EduMember member = eduMemberService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", id);
        List<EduCourse> courseList = eduCourseService.list(wrapper);


        return RMessage.ok().data("member", member).data("list", courseList);
    }

}
