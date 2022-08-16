package com.venson.aclservice.service;

import com.venson.aclservice.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface UserService extends IService<AclUser> {

    // 从数据库中取出用户信息
    AclUser selectByUsername(String username);
}
