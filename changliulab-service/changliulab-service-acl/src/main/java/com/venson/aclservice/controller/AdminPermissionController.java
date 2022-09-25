package com.venson.aclservice.controller;


import com.venson.aclservice.entity.AdminPermission;
import com.venson.aclservice.service.AdminPermissionService;
import com.venson.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/auth/admin/permission")
public class AdminPermissionController {

    @Autowired
    private AdminPermissionService adminPermissionService;


    @GetMapping
    public Result indexAllPermission() {
        List<AdminPermission> list =  adminPermissionService.queryAllMenuLab();
        return Result.success().data("children",list);
    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        adminPermissionService.removeChildByIdLab(id);
        return Result.success();
    }

    @PostMapping("/doAssign")
    public Result doAssign(Long roleId, Long[] permissionId) {
        adminPermissionService.saveRolePermissionRelationShipLab(roleId,permissionId);
        return Result.success();
    }

    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        List<AdminPermission> list = adminPermissionService.selectAllMenu(roleId);
        return Result.success().data("children", list);
    }



    @PostMapping("save")
    public Result save(@RequestBody AdminPermission adminPermission) {
        adminPermissionService.save(adminPermission);
        return Result.success();
    }

    @PutMapping("update")
    public Result updateById(@RequestBody AdminPermission adminPermission) {
        adminPermissionService.updateById(adminPermission);
        return Result.success();
    }

}

