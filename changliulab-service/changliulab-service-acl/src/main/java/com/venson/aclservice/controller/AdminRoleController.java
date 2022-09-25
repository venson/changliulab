package com.venson.aclservice.controller;


import com.venson.aclservice.entity.AdminRole;
import com.venson.aclservice.service.AdminRoleService;
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

    @GetMapping("{page}/{limit}")
    public Result index(
            @PathVariable Long page,

            @PathVariable Long limit,
            AdminRole adminRole) {
        Page<AdminRole> pageParam = new Page<>(page, limit);
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(adminRole.getRoleName())) {
            wrapper.like("role_name", adminRole.getRoleName());
        }
        adminRoleService.page(pageParam,wrapper);
        return Result.success(pageParam);
    }

    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        AdminRole adminRole = adminRoleService.getById(id);
        return Result.success(adminRole);
    }

    @PostMapping("save")
    public Result save(@RequestBody AdminRole adminRole) {
        adminRoleService.save(adminRole);
        return Result.success();
    }

    @PutMapping("update")
    public Result updateById(@RequestBody AdminRole adminRole) {
        adminRoleService.updateById(adminRole);
        return Result.success();
    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        adminRoleService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        adminRoleService.removeByIds(idList);
        return Result.success();
    }
}

