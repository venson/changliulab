package com.venson.aclservice.service;

import com.venson.aclservice.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.aclservice.entity.dto.passwordDTO;

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

    void resetRandomPasswordById(Long id);

    void updateAclUserById(AclUser user);

    void updatePassword(passwordDTO passwordDTO);

    void resetPasswordForUser();
}
