package com.venson.aclservice.controller;
import com.venson.aclservice.entity.AdminUser;
import com.venson.aclservice.entity.dto.AclUserDTO;
import com.venson.aclservice.entity.vo.UserPersonalVO;
import com.venson.aclservice.service.AdminRoleService;
import com.venson.aclservice.service.AdminUserService;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.servicebase.valid.UpdateGroup;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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




    public AdminUserController(AdminUserService adminUserService, AdminRoleService adminRoleService) {
        this.adminUserService = adminUserService;
        this.adminRoleService = adminRoleService;
    }

    @GetMapping("{page}/{limit}")
    public Result<PageResponse<AdminUser>> index(
            @PathVariable Long page,

            @PathVariable Long limit,

             AdminUser userQueryVo) {
        Page<AdminUser> pageParam = new Page<>(page, limit);
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        adminUserService.page(pageParam, wrapper);
        PageResponse<AdminUser> pageResponse = PageUtil.toBean(pageParam);
        return Result.success(pageResponse);
    }
    @GetMapping("{id}")
    public Result<AclUserDTO> get(@PathVariable Long id) {
        AclUserDTO user = adminUserService.getUserById(id);
        return Result.success(user);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('user.add')")
    public Result<String> save(@RequestBody AclUserDTO user) {
        adminUserService.addUser(user);
        return Result.success();
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('user.edit')")
    public Result<String> updateById(@Validated(UpdateGroup.class) @RequestBody AclUserDTO user) {
        adminUserService.updateAclUser(user);
        return Result.success();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user.remove')")
    public Result<String> remove(@PathVariable Long id) {
        adminUserService.removeUserById(id);
        return Result.success();
    }

    @DeleteMapping("batchRemove")
    @PreAuthorize("hasAuthority('user.remove')")
    @Deprecated
    public Result<String> batchRemove(@RequestBody List<String> idList) {
        adminUserService.removeByIds(idList);
        return Result.success();
    }


    @PostMapping("doAssign")
    @PreAuthorize("hasAnyAuthority('user.list', 'role.list')")
    @Deprecated
    public Result<String> doAssign(@RequestParam Long userId, @RequestParam Long[] roleId) {
        adminRoleService.saveUserRoleRelationShip(userId,roleId);
        return Result.success();
    }
    @PostMapping("personal/{id}")
    @PreAuthorize("hasAuthority('user.edit')")
    public Result<String> resetRandomPasswordById(@PathVariable Long id){
        adminUserService.resetRandomPasswordById(id);
        return Result.success();
    }

    @PutMapping("personal")
    public Result<String> updateUserPersonalInfo(@RequestBody UserPersonalVO UserPersonalVO){
        Boolean success = adminUserService.updateUserPersonalInfo(UserPersonalVO);
        return success?Result.success():Result.error("Change Password Error");
    }
}

