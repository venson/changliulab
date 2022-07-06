package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.vo.MemberQuery;
import com.venson.eduservice.service.EduMemberService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-05-02
 */
@Slf4j
@RestController
@RequestMapping("/eduservice/edu-member")
//@CrossOrigin
public class EduMemberController {

    private final EduMemberService memberService;

    @Autowired
    public EduMemberController(EduMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("findAll")
    public RMessage findAllMember(){
        List<EduMember> list = memberService.list(null);
        log.info(list.toString());
        return RMessage.ok().data("items", list);

    }

    @DeleteMapping("{id}")
    public RMessage removeMember(@PathVariable("id") String id){
        boolean result = memberService.removeById(id);
        if (result){
            return RMessage.ok();
        } else return RMessage.error();
    }


    @GetMapping("memberPage/{current}/{recordPerPage}")
    public RMessage memberPageList(@PathVariable Integer current,
                                    @PathVariable Integer recordPerPage){
        Page<EduMember> page = new Page<>(current, recordPerPage);
        log.info(page.toString());
        memberService.page(page,null);
        log.info("------------");
        log.info(page.toString());
        long total = page.getTotal();
        List<EduMember> records = page.getRecords();
        return RMessage.ok().data("total",total).data("records", records);

    }


    @PostMapping("pageMemberCondition/{current}/{recordPerPage}")
    public RMessage pageMemberCondition(@PathVariable Integer current,
                                         @PathVariable Integer recordPerPage,
                                         @RequestBody(required = false) @NonNull MemberQuery memberQuery){
        log.info(memberQuery.toString());
        Page<EduMember> pageMember = new Page<>(current, recordPerPage);
        LambdaQueryWrapper<EduMember> wrapper = new QueryWrapper<EduMember>().lambda();
        String name = memberQuery.getName();
        Integer level = memberQuery.getLevel();
        String begin = memberQuery.getBegin();
        String end = memberQuery.getEnd();

        if(!ObjectUtils.isEmpty(name)){
            wrapper.like(EduMember::getName,name);
        }
        if(!ObjectUtils.isEmpty(level)){
            wrapper.eq(EduMember::getLevel,level);
        }
        if(!ObjectUtils.isEmpty(begin)){
            wrapper.ge(EduMember::getGmtCreate, begin);
        }
        if(!ObjectUtils.isEmpty(end)){
            wrapper.le(EduMember::getGmtCreate, end);
        }
        wrapper.orderByDesc(EduMember::getGmtCreate);
        memberService.page(pageMember,wrapper);
        long total = pageMember.getTotal();
        List<EduMember> records = pageMember.getRecords();

        return RMessage.ok().data("total",total).data("row", records);

    }


    @PostMapping("addMember")
    public RMessage addMember(@RequestBody EduMember eduMember){
        boolean save = memberService.save(eduMember);
        if (save) {
            return RMessage.ok();
        }else {
            return RMessage.error();
        }
    }

    @GetMapping("getMember/{id}")
    public RMessage getMember(@PathVariable String id){
        EduMember member = memberService.getById(id);
        if (!ObjectUtils.isEmpty(member)){
            return RMessage.ok().data("member", member);
        }else{
            return RMessage.error();
        }
    }


    @PostMapping("updateMember")
    public RMessage updateMember(@RequestBody EduMember member){
        boolean successFlag = memberService.updateById(member);
        return successFlag? RMessage.ok() : RMessage.error();
    }

    @PutMapping("{id}")
    public RMessage updateMemberPut(@PathVariable String id,
                                     @RequestBody EduMember member){
        member.setId(id);
        boolean successFlag = memberService.updateById(member);
        return successFlag? RMessage.ok() : RMessage.error();
    }



}
