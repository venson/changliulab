package com.venson.educenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.venson.commonutils.RMessage;
import com.venson.educenter.entity.UcenterMember;
import com.venson.educenter.service.UcenterMemberService;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    private final UcenterMemberService ucenterMemberService;

    public UcenterMemberController(UcenterMemberService ucenterMemberService) {
        this.ucenterMemberService = ucenterMemberService;
    }

    @PostMapping("login")
    public RMessage login(@RequestBody UcenterMember ucenterMember){
        String token = ucenterMemberService.login(ucenterMember);
        return RMessage.ok().data("token", token);

    }
    @PostMapping("register")
    public RMessage register(@RequestBody UcenterMember ucenterMember){
        String token = ucenterMemberService.register(ucenterMember);
        return RMessage.ok().data("token", token);

    }
}
