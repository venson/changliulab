package com.venson.aclservice.controller;

import com.venson.aclservice.entity.FrontUser;
import com.venson.aclservice.entity.vo.FrontUserResetPasswordVo;
import com.venson.aclservice.entity.vo.RegistrationVo;
import com.venson.aclservice.entity.vo.ResetPasswordVo;
import com.venson.aclservice.entity.vo.UserLogin;
import com.venson.aclservice.feign.MsmFeignClient;
import com.venson.aclservice.service.FrontUserService;
import com.venson.commonutils.RandomString;
import com.venson.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/front/user")
public class FrontUserController {
    @Autowired
    private FrontUserService frontUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MsmFeignClient msmFeignClient;


    @PostMapping("register")
    public Result register(@RequestBody RegistrationVo vo){
        frontUserService.register(vo);
        return Result.success();
    }
    @PostMapping("resetPassword")
    public Result resetPassword(@RequestBody FrontUserResetPasswordVo vo){
        Boolean success = frontUserService.resetPassword(vo);
        return success?Result.success():Result.error();
    }
}
