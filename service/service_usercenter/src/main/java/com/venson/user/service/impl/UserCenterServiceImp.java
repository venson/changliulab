package com.venson.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.venson.commonutils.JwtUtils;
import com.venson.user.config.BCryptPasswordNotFound;
import com.venson.user.entity.UserCenterMember;
import com.venson.user.entity.vo.RegistrationVo;
import com.venson.user.mapper.UcenterMemberMapper;
import com.venson.user.service.UserCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-05-24
 */
@Service
@Slf4j
public class UserCenterServiceImp extends ServiceImpl<UcenterMemberMapper, UserCenterMember> implements UserCenterService {

    private final RedisTemplate<String,String> redisTemplate;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public UserCenterServiceImp(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String login(UserCenterMember userCenterMember) {
        String email = userCenterMember.getEmail();
        String password = userCenterMember.getPassword();
        if(ObjectUtils.isEmpty(email) || ObjectUtils.isEmpty(password)){
            throw new CustomizedException(20001, "invalid email or password");
        }
        System.out.println(userCenterMember.getId());
        QueryWrapper<UserCenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        UserCenterMember one = baseMapper.selectOne(wrapper);
        if(one == null) {
            passwordEncoder.matches(password, BCryptPasswordNotFound.instance);
        }else{
            if(passwordEncoder.matches(password, one.getPassword())){
                return JwtUtils.getJwtToken(one.getId(), one.getNickname());
            }
        }
        return "";
    }

    @Override
    public String register(RegistrationVo vo) {
        String securityCode = vo.getSecurityCode();
        String nickName = vo.getNickName();
        String email = vo.getEmail();
        String password = vo.getPassword();
        if(ObjectUtils.isEmpty(email) || ObjectUtils.isEmpty(nickName)
            || ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(securityCode)){
            throw new CustomizedException(20001, "invalid information");
        }

        String code = redisTemplate.opsForValue().get(email);
        if(!securityCode.equals(code)){
            throw new CustomizedException(200001, "Security code error");
        }

        QueryWrapper<UserCenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Long count = baseMapper.selectCount(wrapper);
        if (count >0){
            throw new CustomizedException(200001, "duplicate email address");
        }

        UserCenterMember userCenterMember = new UserCenterMember();
//        BeanUtils.copyProperties(vo,ucenterMember, "password");
        userCenterMember.setNickname(vo.getNickName());
        userCenterMember.setEmail(vo.getEmail());
        log.info(userCenterMember.toString());
        userCenterMember.setPassword(passwordEncoder.encode(password));

        baseMapper.insert(userCenterMember);


        return null;
    }

    @Override
    public List<UserCenterMember> getMemberList(String filter) {
        LambdaQueryWrapper<UserCenterMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UserCenterMember::getId,
                UserCenterMember::getNickname,
                UserCenterMember::getEmail);
        if(StringUtils.hasText(filter)){
           wrapper.like(UserCenterMember::getNickname,filter).or()
                   .like(UserCenterMember::getEmail,filter);
        }
        return baseMapper.selectList(wrapper);
    }
}
