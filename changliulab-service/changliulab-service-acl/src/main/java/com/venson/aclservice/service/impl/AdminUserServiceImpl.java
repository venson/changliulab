package com.venson.aclservice.service.impl;

import com.venson.aclservice.entity.AdminUser;
import com.venson.aclservice.entity.dto.ChangePasswordDTO;
import com.venson.aclservice.entity.vo.ResetPasswordVo;
import com.venson.aclservice.feign.MsmFeignClient;
import com.venson.aclservice.mapper.AdminUserMapper;
import com.venson.aclservice.service.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.Result;
import com.venson.commonutils.RandomString;
import com.venson.security.entity.AuthContext;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MsmFeignClient msmFeignClient;

    @Override
    public AdminUser selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<AdminUser>().eq("username", username));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetRandomPasswordById(Long id) {
        String randomPassword = RandomString.randomCode(10);
        AdminUser user = baseMapper.selectById(id);
        user.setRandomPassword(true);
        user.setPassword(passwordEncoder.encode(randomPassword));
        baseMapper.updateById(user);
        ResetPasswordVo passwordVo = new ResetPasswordVo();
        passwordVo.setEmail(user.getEmail());
        passwordVo.setRandomPassword(randomPassword);
        Result result = msmFeignClient.resetEmail(passwordVo);
        if(!result.getSuccess()){
            throw new CustomizedException(20001,"Email Sent Failed");
        }
    }

    @Override
    public void updateAclUserById(AdminUser user) {
        AdminUser adminUser = baseMapper.selectById(user.getId());
        Assert.notNull(user.getNickName(),"nickname can not be empty");
        adminUser.setNickName(user.getNickName());
        adminUser.setAvatar(user.getAvatar());
        Assert.notNull(user.getEmail(),"Email can not be empty");
        adminUser.setEmail(user.getEmail());
        baseMapper.updateById(user);
    }

    @Override
    public void updatePassword(ChangePasswordDTO changePasswordDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = AuthContext.get().getId();
        AdminUser adminUser = baseMapper.selectById(userId);
        boolean match = passwordEncoder.matches(changePasswordDTO.getOldPassword(), adminUser.getPassword());
        if(match){
            adminUser.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            baseMapper.updateById(adminUser);
        }
    }

    @Override
    public void resetPasswordForUser() {
        String randomPassword = RandomString.randomCode(10);
        Long userId = AuthContext.get().getId();
        AdminUser adminUser = baseMapper.selectById(userId);
        adminUser.setPassword(passwordEncoder.encode(randomPassword));
        baseMapper.updateById(adminUser);
        ResetPasswordVo passwordVo = new ResetPasswordVo();
        passwordVo.setEmail(adminUser.getEmail());
        passwordVo.setRandomPassword(randomPassword);
        Result result = msmFeignClient.resetEmail(passwordVo);
        if(!result.getSuccess()){
            throw new RuntimeException("Email Sent Failed");
        }
    }
}
