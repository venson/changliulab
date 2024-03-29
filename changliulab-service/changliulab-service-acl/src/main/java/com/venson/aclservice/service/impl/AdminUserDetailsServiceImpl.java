package com.venson.aclservice.service.impl;

import com.venson.aclservice.entity.AdminUser;
import com.venson.aclservice.service.AdminPermissionService;
import com.venson.aclservice.service.AdminUserService;
import com.venson.security.entity.SecurityUser;
import com.venson.security.entity.UserType;
import com.venson.security.entity.bo.UserInfoBO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service("adminUserDetailsService")
public class AdminUserDetailsServiceImpl implements UserDetailsService {

    private final AdminUserService adminUserService;

    private final AdminPermissionService adminPermissionService;

    public AdminUserDetailsServiceImpl(AdminUserService adminUserService, AdminPermissionService adminPermissionService) {
        this.adminUserService = adminUserService;
        this.adminPermissionService = adminPermissionService;
    }

    /***
     * 根据账号获取用户信息
     * @param username:
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        AdminUser adminUser = adminUserService.selectByUsername(username);

        // 判断用户是否存在
        if (null == adminUser){
            throw new UsernameNotFoundException("User not exists");
        }
        // 返回UserDetails实现类
        UserInfoBO userInfoBO = new UserInfoBO();
        BeanUtils.copyProperties(adminUser,userInfoBO);
        userInfoBO.setType(UserType.MEMBER);

        List<String> authorities = adminPermissionService.selectPermissionValueByUserId(adminUser.getId());
        SecurityUser securityUser = new SecurityUser(userInfoBO);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }

}
