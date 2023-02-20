package com.venson.aclservice.controller;

import com.venson.aclservice.entity.vo.FrontUserResetPasswordVo;
import com.venson.aclservice.entity.vo.RegistrationVo;
import com.venson.aclservice.service.FrontUserService;
import com.venson.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/front/user")
public class FrontUserController {
    @Autowired
    private FrontUserService frontUserService;


    @PostMapping("register")
    public Result<String> register(@RequestBody RegistrationVo vo){
        frontUserService.register(vo);
        return Result.success();
    }
    @PostMapping("resetPassword")
    public Result<String> resetPassword(@RequestBody FrontUserResetPasswordVo vo){
        Boolean success = frontUserService.resetPassword(vo);
        return success?Result.success():Result.error();
    }
}
