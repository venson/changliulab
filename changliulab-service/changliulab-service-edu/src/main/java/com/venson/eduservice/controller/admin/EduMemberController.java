package com.venson.eduservice.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.dto.MemberQuery;
import com.venson.eduservice.service.EduMemberService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
@RequestMapping("/eduservice/admin/edu-member")
public class EduMemberController {

    private final EduMemberService memberService;

    @Autowired
    public EduMemberController(EduMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public Result findAllMember(){
        List<EduMember> list = memberService.list(null);
        log.info(list.toString());
        return Result.success().data("items", list);

    }

    @DeleteMapping("{id}")
    public Result removeMember(@PathVariable("id") Long id){
        boolean result = memberService.removeById(id);
        if (result){
            return Result.success();
        } else return Result.error();
    }


    @GetMapping("{pageNum}/{limit}")
    public Result memberPageList(@PathVariable Integer pageNum,
                                 @PathVariable Integer limit){
        Page<EduMember> page = new Page<>(pageNum,limit);
        memberService.page(page,null);
        log.info("------------");
        Map<String, Object> map = PageUtil.toMap(page);
        return Result.success().data(map);

    }


    @PostMapping("{pageNum}/{limit}")
    public Result pageMemberCondition(@PathVariable Integer pageNum,
                                      @PathVariable Integer limit,
                                      @RequestBody(required = false) @NonNull MemberQuery memberQuery){
        Page<EduMember> pageMember = new Page<>(pageNum,limit);
        LambdaQueryWrapper<EduMember> wrapper = new QueryWrapper<EduMember>().lambda();
        if(memberQuery!=null){
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
        }

        wrapper.orderByDesc(EduMember::getId);
        memberService.page(pageMember,wrapper);
        Map<String, Object> map = PageUtil.toMap(pageMember);
        return Result.success().data(map);

    }


    @PostMapping("")
    public Result addMember(@RequestBody EduMember eduMember){
        boolean save = memberService.save(eduMember);
        if (save) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @GetMapping("{id}")
    public Result getMember(@PathVariable Long id){
        EduMember member = memberService.getById(id);
        return Result.success().data("member", member);
    }



    @PutMapping("{id}")
    public Result updateMember(@PathVariable Long id,
                               @RequestBody EduMember memberWeb){

        EduMember member = memberService.getById(id);
        member.setAvatar(memberWeb.getAvatar());
        member.setIntro(memberWeb.getIntro());
        member.setCareer(memberWeb.getCareer());
        member.setLevel(memberWeb.getLevel());
        member.setTitle(memberWeb.getTitle());
        member.setName(memberWeb.getName());
        boolean successFlag = memberService.updateById(member);
        return successFlag? Result.success() : Result.error();
    }



}
