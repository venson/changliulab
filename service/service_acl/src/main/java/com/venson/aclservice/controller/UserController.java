package com.venson.aclservice.controller;
import com.venson.aclservice.entity.Role;
import com.venson.aclservice.entity.AclUser;
import com.venson.aclservice.service.RoleService;
import com.venson.aclservice.service.UserService;
import com.venson.commonutils.MD5;
import com.venson.commonutils.RMessage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("{page}/{limit}")
    public RMessage index(
            @PathVariable Long page,

            @PathVariable Long limit,

             AclUser userQueryVo) {
        Page<AclUser> pageParam = new Page<>(page, limit);
        QueryWrapper<AclUser> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<AclUser> pageModel = userService.page(pageParam, wrapper);
        return RMessage.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }
    @GetMapping("get/{id}")
    public RMessage get(@PathVariable Long id) {
        AclUser user = userService.getById(id);
        return RMessage.ok().data("item",user);
    }

    @PostMapping("save")
    public RMessage save(@RequestBody AclUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return RMessage.ok();
    }

    @PutMapping("update")
    public RMessage updateById(@RequestBody AclUser user) {
        userService.updateById(user);
        return RMessage.ok();
    }

    @DeleteMapping("remove/{id}")
    public RMessage remove(@PathVariable Long id) {
        userService.removeById(id);
        return RMessage.ok();
    }

    @DeleteMapping("batchRemove")
    public RMessage batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return RMessage.ok();
    }

    @GetMapping("/toAssign/{userId}")
    public RMessage toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return RMessage.ok().data(roleMap);
    }

    @PostMapping("/doAssign")
    public RMessage doAssign(@RequestParam Long userId,@RequestParam Long[] roleId) {
        roleService.saveUserRoleRelationShip(userId,roleId);
        return RMessage.ok();
    }
}

