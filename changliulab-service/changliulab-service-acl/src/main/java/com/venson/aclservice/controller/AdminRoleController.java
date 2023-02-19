package com.venson.aclservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.aclservice.entity.AdminRole;
import com.venson.aclservice.entity.dto.AdminRolePermissionDTO;
import com.venson.aclservice.service.AdminRoleService;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/auth/admin/role")
public class AdminRoleController {

    @Autowired
    private AdminRoleService adminRoleService;

    @GetMapping()
    public Result<List<AdminRole>> getAllRoles(){
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(AdminRole::getId,AdminRole::getRoleName,AdminRole::getRoleCode);
        List<AdminRole> list = adminRoleService.list(wrapper);
        return Result.success(list);

    }

    @GetMapping("{page}/{limit}")
    public Result<PageResponse<AdminRole>> index(
            @PathVariable Long page,
            @PathVariable Long limit,
            AdminRole adminRole) {
        Page<AdminRole> pageParam = new Page<>(page, limit);
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(adminRole.getRoleName())) {
            wrapper.like("role_name", adminRole.getRoleName());
        }
        adminRoleService.page(pageParam,wrapper);
        PageResponse<AdminRole> pageRes = PageUtil.toBean(pageParam);
        return Result.success(pageRes);
    }

    @GetMapping("{id}")
    public Result<AdminRole> get(@PathVariable Long id) {
        AdminRole adminRole = adminRoleService.getById(id);
        return Result.success(adminRole);
    }

    @PostMapping()
    public Result<String> save(@RequestBody AdminRolePermissionDTO rolePermissionDTO) {
        adminRoleService.addRoleWithPermissions(rolePermissionDTO);
        return Result.success();
    }

    @PutMapping()
    public Result<String> updateById(@RequestBody AdminRolePermissionDTO rolePermissionDTO) {
        adminRoleService.updateRoleWithPermissions(rolePermissionDTO);
        return Result.success();
    }

    @DeleteMapping("{id}")
    public Result<String> remove(@PathVariable Long id) {
        adminRoleService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("batchRemove")
    public Result<String> batchRemove(@RequestBody List<String> idList) {
        adminRoleService.removeByIds(idList);
        return Result.success();
    }
}

