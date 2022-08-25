package com.venson.aclservice.service.impl;

import com.venson.aclservice.entity.AclUser;
import com.venson.aclservice.service.PermissionService;
import com.venson.aclservice.service.UserService;
import com.venson.security.entity.SecurityUser;
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
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    private final PermissionService permissionService;

    public UserDetailsServiceImpl(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    /***
     * 根据账号获取用户信息
     * @param username:
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        AclUser aclUser = userService.selectByUsername(username);

        // 判断用户是否存在
        if (null == aclUser){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        UserInfoBO userInfoBO = new UserInfoBO();
        BeanUtils.copyProperties(aclUser,userInfoBO);

        List<String> authorities = permissionService.selectPermissionValueByUserId(aclUser.getId());
        SecurityUser securityUser = new SecurityUser(userInfoBO);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }

}
