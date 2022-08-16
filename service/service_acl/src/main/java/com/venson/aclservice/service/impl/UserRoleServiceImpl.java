package com.venson.aclservice.service.impl;

import com.venson.aclservice.entity.AclUserRole;
import com.venson.aclservice.mapper.UserRoleMapper;
import com.venson.aclservice.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, AclUserRole> implements UserRoleService {

}
