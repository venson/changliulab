package com.venson.user.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.venson.commonutils.JwtUtils;
import com.venson.commonutils.RMessage;
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
    public RMessage login(@RequestBody UserCenterMember userCenterMember){
        String token = userCenterService.login(userCenterMember);
        return RMessage.ok().data("token", token);

    }
    @PostMapping("register")
    public RMessage register(@RequestBody RegistrationVo vo){
        String token = userCenterService.register(vo);

        return RMessage.ok().data("token", token);
    }

    @GetMapping("info")
    public RMessage getMemberInfo(HttpServletRequest request){
        Long id = AuthContext.get().getId();
        if(ObjectUtils.isEmpty(id)){
            return RMessage.error() ;
        }
        UserCenterMember member = userCenterService.getById(id);
        member.setPassword("");
        return RMessage.ok().data("user",member);
    }
}
