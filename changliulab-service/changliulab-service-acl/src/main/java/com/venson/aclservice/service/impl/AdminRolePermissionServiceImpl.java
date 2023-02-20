package com.venson.aclservice.service.impl;

//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.aclservice.entity.AdminRolePermission;
import com.venson.aclservice.mapper.AdminRolePermissionMapper;
import com.venson.aclservice.service.AdminRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class AdminRolePermissionServiceImpl extends ServiceImpl<AdminRolePermissionMapper, AdminRolePermission> implements AdminRolePermissionService {

//    @Override
//    public List<AdminRolePermission> getRolePermissionByRoleId(Long id) {
//        LambdaQueryWrapper<AdminRolePermission> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(AdminRolePermission::getRoleId,id).
//                select(AdminRolePermission::getPermissionId,
//                        AdminRolePermission::getId,
//                        AdminRolePermission::getRoleId);
//        return baseMapper.selectList(wrapper);
//
//    }

}
