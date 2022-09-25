package com.venson.aclservice.service;

import com.venson.aclservice.entity.AdminRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface AdminRoleService extends IService<AdminRole> {

    //根据用户获取角色数据
    Map<String, Object> findRoleByUserId(Long userId);

    //根据用户分配角色
    void saveUserRoleRelationShip(Long userId,Long [] roleId);

    List<AdminRole> selectRoleByUserId(Long id);
}
