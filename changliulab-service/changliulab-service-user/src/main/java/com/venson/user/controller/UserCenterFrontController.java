package com.venson.user.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.venson.commonutils.Result;
import com.venson.security.entity.AuthContext;
import com.venson.user.entity.UserCenterMember;
import com.venson.user.entity.vo.RegistrationVo;
import com.venson.user.service.UserCenterService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/front")
public class UserCenterFrontController {
    private final UserCenterService userCenterService;


    public UserCenterFrontController(UserCenterService userCenterService) {
        this.userCenterService = userCenterService;
    }

    @PostMapping("login")
    public Result login(@RequestBody UserCenterMember userCenterMember){
        String token = userCenterService.login(userCenterMember);
        return Result.success().data("token", token);

    }
    @PostMapping("register")
    public Result register(@RequestBody RegistrationVo vo){
        String token = userCenterService.register(vo);

        return Result.success().data("token", token);
    }

    @GetMapping("info")
    public Result getMemberInfo(HttpServletRequest request){
        Long id = AuthContext.get().getId();
        if(ObjectUtils.isEmpty(id)){
            return Result.error() ;
        }
        UserCenterMember member = userCenterService.getById(id);
        member.setPassword("");
        return Result.success().data("user",member);
    }
}
