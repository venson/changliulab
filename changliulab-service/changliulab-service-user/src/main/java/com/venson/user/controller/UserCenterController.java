package com.venson.user.controller;


import com.venson.commonutils.Result;
import com.venson.user.entity.UserCenterMember;
import com.venson.user.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/user/admin")
public class UserCenterController {
    @Autowired
    private UserCenterService userCenterService;

    @GetMapping("")
    public Result getMemberList(@RequestParam(required = false) String filter){
        List<UserCenterMember> memberList = userCenterService.getMemberList(filter);
        return Result.success(memberList);
    }
}
