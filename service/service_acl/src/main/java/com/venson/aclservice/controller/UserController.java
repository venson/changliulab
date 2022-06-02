package com.venson.aclservice.controller;
import com.venson.aclservice.entity.User;
import com.venson.aclservice.service.RoleService;
import com.venson.aclservice.service.UserService;
import com.venson.commonutils.MD5;
import com.venson.commonutils.RMessage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("{page}/{limit}")
    public RMessage index(
            @PathVariable Long page,

            @PathVariable Long limit,

             User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return RMessage.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    @PostMapping("save")
    public RMessage save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return RMessage.ok();
    }

    @PutMapping("update")
    public RMessage updateById(@RequestBody User user) {
        userService.updateById(user);
        return RMessage.ok();
    }

    @DeleteMapping("remove/{id}")
    public RMessage remove(@PathVariable String id) {
        userService.removeById(id);
        return RMessage.ok();
    }

    @DeleteMapping("batchRemove")
    public RMessage batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return RMessage.ok();
    }

    @GetMapping("/toAssign/{userId}")
    public RMessage toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return RMessage.ok().data(roleMap);
    }

    @PostMapping("/doAssign")
    public RMessage doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return RMessage.ok();
    }
}

