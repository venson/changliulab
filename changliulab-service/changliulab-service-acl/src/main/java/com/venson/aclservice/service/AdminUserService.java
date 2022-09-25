package com.venson.aclservice.service;

import com.venson.aclservice.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.aclservice.entity.dto.ChangePasswordDTO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface AdminUserService extends IService<AdminUser> {

    // 从数据库中取出用户信息
    AdminUser selectByUsername(String username);

    void resetRandomPasswordById(Long id);

    void updateAclUserById(AdminUser user);

    void updatePassword(ChangePasswordDTO ChangePasswordDTO);

    void resetPasswordForUser();
}
