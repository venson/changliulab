package com.venson.educenter.controller;

import com.venson.commonutils.RMessage;
import com.venson.educenter.entity.UcenterMember;
import com.venson.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;


    @GetMapping("login")
    public RMessage login(@RequestBody UcenterMember ucenterMember){
        String token = ucenterMemberService.login(ucenterMember);

        return RMessage.ok().data("token", token);
    }

    @PostMapping("register")
    public RMessage register(@RequestBody UcenterMember ucenterMember){
        boolean result = ucenterMemberService.register(ucenterMember);

        return RMessage.ok();
    }

}
