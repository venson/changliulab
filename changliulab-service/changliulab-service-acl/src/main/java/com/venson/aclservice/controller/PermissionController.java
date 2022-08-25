package com.venson.aclservice.controller;


import com.venson.aclservice.entity.Permission;
import com.venson.aclservice.service.PermissionService;
import com.venson.commonutils.RMessage;
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
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @GetMapping
    public RMessage indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuLab();
        return RMessage.ok().data("children",list);
    }

    @DeleteMapping("remove/{id}")
    public RMessage remove(@PathVariable Long id) {
        permissionService.removeChildByIdLab(id);
        return RMessage.ok();
    }

    @PostMapping("/doAssign")
    public RMessage doAssign(Long roleId,Long[] permissionId) {
        permissionService.saveRolePermissionRelationShipLab(roleId,permissionId);
        return RMessage.ok();
    }

    @GetMapping("toAssign/{roleId}")
    public RMessage toAssign(@PathVariable Long roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return RMessage.ok().data("children", list);
    }



    @PostMapping("save")
    public RMessage save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return RMessage.ok();
    }

    @PutMapping("update")
    public RMessage updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return RMessage.ok();
    }

}

