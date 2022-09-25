package com.venson.eduservice.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduMemberScholar;
import com.venson.eduservice.service.EduMemberScholarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result getMemberIdList(@PathVariable String scholarId){
        List<EduMemberScholar> list = memberScholarService.getCurrentMemberByScholarId(scholarId);
        return Result.success().data("item", list);
    }
    @PostMapping("{scholarId}")
    public Result newScholarMember(@PathVariable String scholarId, @RequestBody List<EduMemberScholar> memberList){
        memberList.forEach(o -> o.setScholarId(scholarId));
        memberScholarService.saveBatch(memberList);
        return Result.success();
    }
    @PutMapping("{scholarId}")
    public Result updateMember(@PathVariable String scholarId, @RequestBody List<EduMemberScholar> memberList){
        memberScholarService.updateMemberScholar(scholarId, memberList);
        return Result.success();
    }

    @DeleteMapping("{scholarId}")
    public Result deleteMemberByScholarId(@PathVariable String scholarId){
        QueryWrapper<EduMemberScholar> wrapper = new QueryWrapper<>();
        wrapper.eq("scholar_id", scholarId);
        memberScholarService.remove(wrapper);
        return Result.success();
    }
    @DeleteMapping("member")
    public Result deleteMemberByMemberId(@RequestBody List<String> memberIdList){
        memberScholarService.removeBatchByIds(memberIdList);
        return Result.success();
    }

}
