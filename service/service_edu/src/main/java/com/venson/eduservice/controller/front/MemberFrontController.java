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
import org.springframework.util.ObjectUtils;
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
    private EduCourseService eduCourseService;
    @GetMapping("{page}/{limit}")
    public RMessage getMemberFrontList(@PathVariable Integer page, @PathVariable Integer limit,
                                       @RequestParam(required = false) String level){
        Map<String,Object> map = eduMemberService.getPageFrontMemberList(page,limit,level);
        return RMessage.ok().data(map);
    }

    @GetMapping("{id}")
    public RMessage getMemberFrontById(@PathVariable Long id){
        EduMember member = eduMemberService.getById(id);
        return RMessage.ok().data("member", member);
    }

}
