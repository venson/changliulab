package com.venson.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.venson.aclservice.entity.AdminRole;
import com.venson.aclservice.entity.AdminUser;
import com.venson.aclservice.entity.dto.UserInfoDTO;
import com.venson.aclservice.service.IndexService;
import com.venson.aclservice.service.AdminPermissionService;
import com.venson.aclservice.service.AdminRoleService;
import com.venson.aclservice.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IndexServiceImpl implements IndexService {

    private final AdminUserService adminUserService;

    private final AdminRoleService adminRoleService;

    private final AdminPermissionService adminPermissionService;

    private final RedisTemplate<String, List<String>> redisTemplate;

    public IndexServiceImpl(AdminUserService adminUserService, AdminRoleService adminRoleService,
                            AdminPermissionService adminPermissionService,
                            RedisTemplate<String,List<String>> redisTemplate) {
        this.adminUserService = adminUserService;
        this.adminRoleService = adminRoleService;
        this.adminPermissionService = adminPermissionService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据用户名获取用户登录信息
     *
     */
    public UserInfoDTO getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        AdminUser user = adminUserService.selectByUsername(username);
        if (null == user) {
            log.info(username + "not found");
            return null;
        }

        //根据用户id获取角色
        List<AdminRole> adminRoleList = adminRoleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = adminRoleList.stream().map(AdminRole::getRoleName).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = adminPermissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        List<JSONObject> permissionList = getMenu(username);

        return new UserInfoDTO(user.getId(),user.getUsername(),user.getAvatar(),roleNameList,permissionValueList,permissionList);

    }

    /**
     * 根据用户名获取动态菜单
     */
    public List<JSONObject> getMenu(String username) {
        AdminUser user = adminUserService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        return adminPermissionService.selectPermissionByUserId(user.getId());
    }


}
