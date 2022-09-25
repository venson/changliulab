package com.venson.aclservice.controller;
import com.venson.aclservice.entity.AdminUser;
import com.venson.aclservice.entity.dto.ChangePasswordDTO;
import com.venson.aclservice.service.AdminRoleService;
import com.venson.aclservice.service.AdminUserService;
import com.venson.commonutils.Result;
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
@RequestMapping("/auth/admin/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    private final AdminRoleService adminRoleService;

    private final PasswordEncoder passwordEncoder;


    public AdminUserController(AdminUserService adminUserService, AdminRoleService adminRoleService, PasswordEncoder passwordEncoder) {
        this.adminUserService = adminUserService;
        this.adminRoleService = adminRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("{page}/{limit}")
    public Result index(
            @PathVariable Long page,

            @PathVariable Long limit,

             AdminUser userQueryVo) {
        Page<AdminUser> pageParam = new Page<>(page, limit);
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        Page<AdminUser> pageModel = adminUserService.page(pageParam, wrapper);
        return Result.success(pageModel);
    }
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        AdminUser user = adminUserService.getById(id);
        user.setPassword(null);
        return Result.success(user);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('user.add')")
    public Result save(@RequestBody AdminUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        adminUserService.save(user);
        return Result.success();
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('user.edit')")
    public Result updateById(@RequestBody AdminUser user) {
        user.setPassword(null);
        adminUserService.updateAclUserById(user);
        return Result.success();
    }

    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAuthority('user.remove')")
    public Result remove(@PathVariable Long id) {
        adminUserService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("batchRemove")
    @PreAuthorize("hasAuthority('user.remove')")
    public Result batchRemove(@RequestBody List<String> idList) {
        adminUserService.removeByIds(idList);
        return Result.success();
    }

    @GetMapping("/toAssign/{userId}")
    @PreAuthorize("hasAuthority('role.list')")
    public Result toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = adminRoleService.findRoleByUserId(userId);
        return Result.success(roleMap);
    }

    @PostMapping("/doAssign")
    @PreAuthorize("hasAnyAuthority('user.list', 'role.list')")
    public Result doAssign(@RequestParam Long userId, @RequestParam Long[] roleId) {
        adminRoleService.saveUserRoleRelationShip(userId,roleId);
        return Result.success();
    }
    @PostMapping("/password/{id}")
    @PreAuthorize("hasAuthority('user.edit')")
    public Result resetRandomPasswordById(@PathVariable Long id){
        adminUserService.resetRandomPasswordById(id);
        return Result.success();
    }
    @PostMapping("/password")
    public Result resetPasswordForUser(){
        adminUserService.resetPasswordForUser();
        return Result.success();
    }

    @PutMapping("password")
    public Result updatePassword(@RequestBody ChangePasswordDTO ChangePasswordDTO){
        adminUserService.updatePassword(ChangePasswordDTO);
        return Result.success();
    }
}

