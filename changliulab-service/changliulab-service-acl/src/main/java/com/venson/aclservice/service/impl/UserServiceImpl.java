package com.venson.aclservice.service.impl;

import com.venson.aclservice.entity.AclUser;
import com.venson.aclservice.entity.dto.passwordDTO;
import com.venson.aclservice.entity.vo.ResetPasswordVo;
import com.venson.aclservice.feign.MsmFeignClient;
import com.venson.aclservice.mapper.UserMapper;
import com.venson.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.RMessage;
import com.venson.commonutils.RandomString;
import com.venson.security.entity.AuthContext;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, AclUser> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MsmFeignClient msmFeignClient;

    @Override
    public AclUser selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<AclUser>().eq("username", username));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetRandomPasswordById(Long id) {
        String randomPassword = RandomString.randomCode(10);
        AclUser user = baseMapper.selectById(id);
        user.setRandomPassword(true);
        user.setPassword(passwordEncoder.encode(randomPassword));
        baseMapper.updateById(user);
        ResetPasswordVo passwordVo = new ResetPasswordVo();
        passwordVo.setEMail(user.getEMail());
        passwordVo.setRandomPassword(randomPassword);
        RMessage rMessage = msmFeignClient.resetEmail(passwordVo);
        if(!rMessage.getSuccess()){
            throw new RuntimeException("Email Sent Failed");
        }
    }

    @Override
    public void updateAclUserById(AclUser user) {
        AclUser aclUser = baseMapper.selectById(user.getId());
        Assert.notNull(user.getNickName(),"nickname can not be empty");
        aclUser.setNickName(user.getNickName());
        Assert.notNull(user.getAvatar(),"Avatar can not be empty");
        aclUser.setAvatar(user.getAvatar());
        Assert.notNull(user.getEMail(),"Email can not be empty");
        aclUser.setEMail(user.getEMail());
        baseMapper.updateById(user);
    }

    @Override
    public void updatePassword(passwordDTO passwordDTO) {
        Long userId = AuthContext.get().getId();
        AclUser aclUser = baseMapper.selectById(userId);
        boolean match = passwordEncoder.matches(passwordDTO.getOldPassword(), aclUser.getPassword());
        if(match){
            aclUser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            baseMapper.updateById(aclUser);
        }
    }

    @Override
    public void resetPasswordForUser() {
        String randomPassword = RandomString.randomCode(10);
        Long userId = AuthContext.get().getId();
        AclUser aclUser = baseMapper.selectById(userId);
        aclUser.setPassword(passwordEncoder.encode(randomPassword));
        baseMapper.updateById(aclUser);
        ResetPasswordVo passwordVo = new ResetPasswordVo();
        passwordVo.setEMail(aclUser.getEMail());
        passwordVo.setRandomPassword(randomPassword);
        RMessage rMessage = msmFeignClient.resetEmail(passwordVo);
        if(!rMessage.getSuccess()){
            throw new RuntimeException("Email Sent Failed");
        }
    }
}
