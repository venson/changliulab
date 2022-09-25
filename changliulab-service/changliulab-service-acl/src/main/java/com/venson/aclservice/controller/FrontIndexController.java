package com.venson.aclservice.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.venson.aclservice.entity.FrontUser;
import com.venson.aclservice.service.FrontUserService;
import com.venson.commonutils.Result;
import com.venson.security.entity.AuthContext;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/front/index")
public class FrontIndexController {
    @Autowired
    private FrontUserService frontUserService;

    @GetMapping("info")
    public Result getMemberInfo(){
        UserContextInfoBO userContext = ContextUtils.getUserContext();

        if(userContext!=null){
            Long userId = userContext.getId();
            FrontUser member = frontUserService.getById(userId);
            member.setPassword("");
            return Result.success().data("user",member);
        }
        return Result.error();
    }
}
