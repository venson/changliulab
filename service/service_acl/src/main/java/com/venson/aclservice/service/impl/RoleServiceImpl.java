package com.venson.aclservice.service.impl;

import com.venson.aclservice.entity.Role;
import com.venson.aclservice.entity.AclUserRole;
import com.venson.aclservice.mapper.RoleMapper;
import com.venson.aclservice.service.RoleService;
import com.venson.aclservice.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;


    //根据用户获取角色数据
    @Override
    public Map<String, Object> findRoleByUserId(Long userId) {
        //查询所有的角色
        List<Role> allRolesList =baseMapper.selectList(null);

        //根据用户id，查询用户拥有的角色id
        List<AclUserRole> existUserRoleList = userRoleService.list(new QueryWrapper<AclUserRole>().eq("user_id", userId).select("role_id"));

        List<Long> existRoleList = existUserRoleList.stream().map(AclUserRole::getRoleId).collect(Collectors.toList());

        //对角色进行分类
        List<Role> assignRoles = new ArrayList<>();
        for (Role role : allRolesList) {
            //已分配
            if(existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    //根据用户分配角色
    @Override
    public void saveUserRoleRelationShip(Long userId,Long[] roleIds) {
        userRoleService.remove(new QueryWrapper<AclUserRole>().eq("user_id", userId));

        List<AclUserRole> userRoleList = new ArrayList<>();
        for(Long roleId : roleIds) {
            if(ObjectUtils.isEmpty(roleId)) continue;
            AclUserRole userRole = new AclUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);

            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<Role> selectRoleByUserId(Long id) {
        //根据用户id拥有的角色id
        List<AclUserRole> userRoleList = userRoleService.list(new QueryWrapper<AclUserRole>().eq("user_id", id).select("role_id"));
        List<Long> roleIdList = userRoleList.stream().map(AclUserRole::getRoleId).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
