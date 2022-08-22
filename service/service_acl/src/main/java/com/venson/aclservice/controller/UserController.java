package com.venson.aclservice.controller;
import com.venson.aclservice.entity.AclUser;
import com.venson.aclservice.entity.dto.passwordDTO;
import com.venson.aclservice.service.RoleService;
import com.venson.aclservice.service.UserService;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.RMessage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
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

        Page<AclUser> pageModel = userService.page(pageParam, wrapper);
        Map<String, Object> map = PageUtil.toMap(pageModel);
        return RMessage.ok().data(map);
    }
    @GetMapping("get/{id}")
    public RMessage get(@PathVariable Long id) {
        AclUser user = userService.getById(id);
        user.setPassword(null);
        return RMessage.ok().data("item",user);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('user.add')")
    public RMessage save(@RequestBody AclUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return RMessage.ok();
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('user.edit')")
    public RMessage updateById(@RequestBody AclUser user) {
        user.setPassword(null);
        userService.updateAclUserById(user);
        return RMessage.ok();
    }

    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAuthority('user.remove')")
    public RMessage remove(@PathVariable Long id) {
        userService.removeById(id);
        return RMessage.ok();
    }

    @DeleteMapping("batchRemove")
    @PreAuthorize("hasAuthority('user.remove')")
    public RMessage batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return RMessage.ok();
    }

    @GetMapping("/toAssign/{userId}")
    @PreAuthorize("hasAuthority('role.list')")
    public RMessage toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return RMessage.ok().data(roleMap);
    }

    @PostMapping("/doAssign")
    @PreAuthorize("hasAnyAuthority('user.list', 'role.list')")
    public RMessage doAssign(@RequestParam Long userId,@RequestParam Long[] roleId) {
        roleService.saveUserRoleRelationShip(userId,roleId);
        return RMessage.ok();
    }
    @PostMapping("/password/{id}")
    @PreAuthorize("hasAuthority('user.edit')")
    public RMessage resetRandomPasswordById(@PathVariable Long id){
        userService.resetRandomPasswordById(id);
        return RMessage.ok();
    }
    @PostMapping("/password")
    public RMessage resetPasswordForUser(){
        userService.resetPasswordForUser();
        return RMessage.ok();
    }

    @PutMapping("password")
    public RMessage updatePassword(@RequestBody passwordDTO passwordDTO){
        userService.updatePassword(passwordDTO);
        return RMessage.ok();
    }
}

