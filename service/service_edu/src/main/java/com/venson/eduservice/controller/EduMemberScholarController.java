package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduMemberScholar;
import com.venson.eduservice.service.EduMemberScholarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/eduservice/admin/edu-member-scholar")
public class EduMemberScholarController {
    @Autowired
    private EduMemberScholarService memberScholarService;

    @GetMapping("{scholarId}")
    public RMessage getMemberIdList(@PathVariable String scholarId){
        List<EduMemberScholar> list = memberScholarService.getCurrentMemberByScholarId(scholarId);
        return RMessage.ok().data("item", list);
    }
    @PostMapping("{scholarId}")
    public RMessage newScholarMember(@PathVariable String scholarId, @RequestBody List<EduMemberScholar> memberList){
        memberList.forEach(o -> o.setScholarId(scholarId));
        memberScholarService.saveBatch(memberList);
        return RMessage.ok();
    }
    @PutMapping("{scholarId}")
    public RMessage updateMember(@PathVariable String scholarId, @RequestBody List<EduMemberScholar> memberList){
        memberScholarService.updateMemberScholar(scholarId, memberList);
        return RMessage.ok();
    }

    @DeleteMapping("{scholarId}")
    public RMessage deleteMemberByScholarId(@PathVariable String scholarId){
        QueryWrapper<EduMemberScholar> wrapper = new QueryWrapper<>();
        wrapper.eq("scholar_id", scholarId);
        memberScholarService.remove(wrapper);
        return RMessage.ok();
    }
    @DeleteMapping("member")
    public RMessage deleteMemberByMemberId(@RequestBody List<String> memberIdList){
        memberScholarService.removeBatchByIds(memberIdList);
        return RMessage.ok();
    }

}
