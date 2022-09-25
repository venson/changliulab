package com.venson.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.venson.aclservice.entity.dto.UserInfoDTO;
import com.venson.aclservice.service.IndexService;
import com.venson.commonutils.Result;
import com.venson.security.entity.UserType;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/admin/index")
public class AdminIndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public Result info(){
        //获取当前登录用户用户名

        UserContextInfoBO userContext = ContextUtils.getUserContext();
        if(userContext!=null && userContext.getType() == UserType.MEMBER){
            String username = userContext.getUsername();
            if("anonymousUser".equals(username)){
                return Result.illegalToken();
            }
            UserInfoDTO userInfoDTO = indexService.getUserInfo(username);
            return Result.success(userInfoDTO);
        }
        return Result.unAuthorized();
    }

    /**
     * 获取菜单
     */
    @GetMapping("menu")
    public Result getMenu(){
        //获取当前登录用户用户名
        UserContextInfoBO userContext = ContextUtils.getUserContext();
        if(userContext!=null && userContext.getType() == UserType.MEMBER){
            List<JSONObject> permissionList = indexService.getMenu(userContext.getUsername());
            return Result.success().data("permissionList", permissionList);
        }
        return Result.unAuthorized();
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.success();
    }

}
