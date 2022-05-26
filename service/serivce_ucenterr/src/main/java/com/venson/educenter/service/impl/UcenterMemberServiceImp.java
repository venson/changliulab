package com.venson.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.venson.commonutils.JwtUtils;
import com.venson.educenter.entity.UcenterMember;
import com.venson.educenter.entity.vo.RegistrationVo;
import com.venson.educenter.mapper.UcenterMemberMapper;
import com.venson.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
public class UcenterMemberServiceImp extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String login(UcenterMember ucenterMember) {
        System.out.println(ucenterMember);
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        String email = ucenterMember.getEmail();
        String password = ucenterMember.getPassword();
        if(ObjectUtils.isEmpty(email) || ObjectUtils.isEmpty(password)){
            throw new CustomizedException(20001, "invalid email or password");
        }
        wrapper.eq("email", email);
        UcenterMember one = baseMapper.selectOne(wrapper);
        System.out.println(one);
        if(one == null) {
            throw new CustomizedException(20001,"invalid email or password");
        }
        boolean matches = passwordEncoder.matches(password, one.getPassword());
        if(matches){
            return JwtUtils.getJwtToken(one.getId(), one.getNickname());
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

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Long count = baseMapper.selectCount(wrapper);
        if (count >0){
            throw new CustomizedException(200001, "duplicate email address");
        }

        UcenterMember ucenterMember = new UcenterMember();
//        BeanUtils.copyProperties(vo,ucenterMember, "password");
        ucenterMember.setNickname(vo.getNickName());
        ucenterMember.setEmail(vo.getEmail());
        log.info(ucenterMember.toString());
        ucenterMember.setPassword(passwordEncoder.encode(password));

        baseMapper.insert(ucenterMember);


        return null;
    }
}
