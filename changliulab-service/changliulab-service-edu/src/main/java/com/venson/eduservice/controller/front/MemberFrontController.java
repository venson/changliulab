package com.venson.eduservice.controller.front;

import com.venson.commonutils.PageResponse;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.enums.MemberLevel;
import com.venson.eduservice.service.front.MemberFrontService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eduservice/front/member")
@Slf4j
public class MemberFrontController {

    @Autowired
    private MemberFrontService memberFrontService;


    @GetMapping()
    public Result<List<EduMember>> getPIMemberFront(){
        List<EduMember> PIMembers = memberFrontService.getPIMemberFront();
        return Result.success(PIMembers);
    }


    @GetMapping(value = "{page}/{limit}", params = {"level"})
    public Result<PageResponse<EduMember>> getMemberFrontList(@PathVariable Integer page, @PathVariable Integer limit,
                                                              @RequestParam MemberLevel level){
        PageResponse<EduMember> pageRes = memberFrontService.getPageFrontMemberListByLevel(page, limit, level);
        return Result.success(pageRes);
    }

    @GetMapping("{id}")
    public Result<EduMember> getMemberFrontByID(@PathVariable Long id){
        EduMember member = memberFrontService.getMemberFrontByID(id);
        return Result.success(member);
    }


}
