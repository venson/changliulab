package com.venson.aclservice.service.impl;

import com.venson.aclservice.entity.AdminRole;
import com.venson.aclservice.entity.AdminUserRole;
import com.venson.aclservice.mapper.AdminRoleMapper;
import com.venson.aclservice.service.AdminRoleService;
import com.venson.aclservice.service.AdminUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

    @Autowired
    private AdminUserRoleService adminUserRoleService;


    //根据用户获取角色数据
    @Override
    public Map<String, Object> findRoleByUserId(Long userId) {
        //查询所有的角色
        List<AdminRole> allRolesList =baseMapper.selectList(null);

        //根据用户id，查询用户拥有的角色id
        List<AdminUserRole> existUserRoleList = adminUserRoleService.list(new QueryWrapper<AdminUserRole>().eq("user_id", userId).select("role_id"));

        List<Long> existRoleList = existUserRoleList.stream().map(AdminUserRole::getRoleId).collect(Collectors.toList());

        //对角色进行分类
        List<AdminRole> assignAdminRoles = new ArrayList<>();
        for (AdminRole adminRole : allRolesList) {
            //已分配
            if(existRoleList.contains(adminRole.getId())) {
                assignAdminRoles.add(adminRole);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignAdminRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    //根据用户分配角色
    @Override
    public void saveUserRoleRelationShip(Long userId,Long[] roleIds) {
        adminUserRoleService.remove(new QueryWrapper<AdminUserRole>().eq("user_id", userId));

        List<AdminUserRole> userRoleList = new ArrayList<>();
        for(Long roleId : roleIds) {
            if(ObjectUtils.isEmpty(roleId)) continue;
            AdminUserRole userRole = new AdminUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);

            userRoleList.add(userRole);
        }
        adminUserRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<AdminRole> selectRoleByUserId(Long id) {
        //根据用户id拥有的角色id
        List<AdminUserRole> userRoleList = adminUserRoleService.list(new QueryWrapper<AdminUserRole>().eq("user_id", id).select("role_id"));
        List<Long> roleIdList = userRoleList.stream().map(AdminUserRole::getRoleId).collect(Collectors.toList());
        List<AdminRole> adminRoleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            adminRoleList = baseMapper.selectBatchIds(roleIdList);
        }
        return adminRoleList;
    }
}
