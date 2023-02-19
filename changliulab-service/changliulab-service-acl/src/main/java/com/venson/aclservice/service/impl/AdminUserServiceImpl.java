package com.venson.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.aclservice.entity.AdminUser;
import com.venson.aclservice.entity.AdminUserRole;
import com.venson.aclservice.entity.dto.AclUserDTO;
import com.venson.aclservice.entity.vo.UserPersonalVO;
import com.venson.aclservice.mapper.AdminUserMapper;
import com.venson.aclservice.service.AdminUserRoleService;
import com.venson.aclservice.service.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.Result;
import com.venson.commonutils.RandomString;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.utils.ContextUtils;
import com.venson.servicebase.entity.ResetPasswordVo;
import com.venson.servicebase.exception.CustomizedException;
import com.venson.servicebase.feign.MsmFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
@Slf4j
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminUserRoleService userRoleService;


    @Autowired
    private MsmFeign msmFeign;

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
        Result<String> result = msmFeign.resetEmail(passwordVo);
        if (!result.getSuccess()) {
            throw new CustomizedException(20001, "Email Sent Failed");
        }
    }

    @Override
    public void updateAclUser(AclUserDTO user) {
        AdminUser adminUser = baseMapper.selectById(user.getId());
        BeanUtils.copyProperties(user, adminUser);
        baseMapper.updateById(adminUser);
        userRoleService.updateUserRole(user.getId(), user.getRoleIds());
    }

    @Override
    public Boolean updateUserPersonalInfo(UserPersonalVO userPersonalVO) {
        UserContextInfoBO userContext = ContextUtils.getUserContext();
        Assert.notNull(userContext, "Invalid request");
        Long userId = userContext.getId();
        AdminUser adminUser = baseMapper.selectById(userId);
        boolean match = passwordEncoder.matches(userPersonalVO.getOld(), adminUser.getPassword());
        Assert.isTrue(match, "Password error");
        String newPassword = userPersonalVO.getAltered();
        if (newPassword != null) {
            adminUser.setPassword(passwordEncoder.encode(userPersonalVO.getAltered()));
            log.info(adminUser.getId() + "request password");
        }
        if (StringUtils.hasText(userPersonalVO.getNickname())) {
            adminUser.setNickName(userPersonalVO.getNickname());
        }
        if (userPersonalVO.getEmail() != null) {
            adminUser.setEmail(userPersonalVO.getEmail());
        }

        baseMapper.updateById(adminUser);
        return true;
    }

//    @Override
//    public void resetPasswordForUser() {
//        String randomPassword = RandomString.randomCode(10);
//        Long userId = AuthContext.get().getId();
//        AdminUser adminUser = baseMapper.selectById(userId);
//        adminUser.setPassword(passwordEncoder.encode(randomPassword));
//        baseMapper.updateById(adminUser);
//        ResetPasswordVo passwordVo = new ResetPasswordVo();
//        passwordVo.setEmail(adminUser.getEmail());
//        passwordVo.setRandomPassword(randomPassword);
//        Result result = msmFeign.resetEmail(passwordVo);
//        if (!result.getSuccess()) {
//            throw new RuntimeException("Email Sent Failed");
//        }
//    }

    @Override
    public AclUserDTO getUserById(Long id) {
        AdminUser adminUser = baseMapper.selectById(id);
        LambdaQueryWrapper<AdminUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUserRole::getUserId, id).select(AdminUserRole::getRoleId);
        List<Long> roleIds = userRoleService.listObjs(wrapper, (o) -> Long.valueOf(o.toString()));
        AclUserDTO userDTO = new AclUserDTO();
        BeanUtils.copyProperties(adminUser, userDTO, "password");
        userDTO.setRoleIds(roleIds);
        return userDTO;

    }

    @Override
    @Transactional
    public void addUser(AclUserDTO user) {
        String email = user.getEmail();
        String username = user.getUsername();
        LambdaQueryWrapper<AdminUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUser::getNickName, username);
        if (baseMapper.selectCount(wrapper) != 0) {
            throw new CustomizedException(20001, "User name already exists");
        }
        wrapper.clear();
        wrapper.eq(AdminUser::getEmail, email);
        if (baseMapper.selectCount(wrapper) != 0) {
            throw new CustomizedException(20001, "Email already exists");
        }

        // add new User
        // 1. generate random password
        String password = RandomString.randomCode(10);
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(user, adminUser);
        adminUser.setRandomPassword(true);
        adminUser.setPassword(passwordEncoder.encode(password));
        baseMapper.insert(adminUser);
        // 2. send password to email
        ResetPasswordVo passwordVo = new ResetPasswordVo(email, password);
        msmFeign.resetEmail(passwordVo);
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        baseMapper.deleteById(id);
        LambdaQueryWrapper<AdminUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUserRole::getUserId, id);
        userRoleService.remove(wrapper);

    }
}
