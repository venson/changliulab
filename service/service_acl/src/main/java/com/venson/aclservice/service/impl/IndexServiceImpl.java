package com.venson.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.venson.aclservice.entity.Role;
import com.venson.aclservice.entity.AclUser;
import com.venson.aclservice.service.IndexService;
import com.venson.aclservice.service.PermissionService;
import com.venson.aclservice.service.RoleService;
import com.venson.aclservice.service.UserService;
import com.venson.commonutils.ResultCode;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    private final UserService userService;

    private final RoleService roleService;

    private final PermissionService permissionService;

    private final RedisTemplate<String, List<String>> redisTemplate;

    public IndexServiceImpl(UserService userService, RoleService roleService,
                            PermissionService permissionService,
                            RedisTemplate<String,List<String>> redisTemplate) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据用户名获取用户登录信息
     *
     */
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        AclUser user = userService.selectByUsername(username);
        if (null == user) {
            throw new CustomizedException(200001,"1111");
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getUsername());
        result.put("id", user.getId());
        result.put("avatar", user.getAvatar());
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     */
    public List<JSONObject> getMenu(String username) {
        AclUser user = userService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        return permissionService.selectPermissionByUserId(user.getId());
    }


}
