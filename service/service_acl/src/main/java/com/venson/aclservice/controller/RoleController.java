package com.venson.aclservice.controller;


import com.venson.aclservice.entity.Role;
import com.venson.aclservice.service.RoleService;
import com.venson.commonutils.RMessage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
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
@RequestMapping("/admin/acl/role")
//@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("{page}/{limit}")
    public RMessage index(
            @PathVariable Long page,

            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        return RMessage.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @GetMapping("get/{id}")
    public RMessage get(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return RMessage.ok().data("item", role);
    }

    @PostMapping("save")
    public RMessage save(@RequestBody Role role) {
        roleService.save(role);
        return RMessage.ok();
    }

    @PutMapping("update")
    public RMessage updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return RMessage.ok();
    }

    @DeleteMapping("remove/{id}")
    public RMessage remove(@PathVariable Long id) {
        roleService.removeById(id);
        return RMessage.ok();
    }

    @DeleteMapping("batchRemove")
    public RMessage batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return RMessage.ok();
    }
}

