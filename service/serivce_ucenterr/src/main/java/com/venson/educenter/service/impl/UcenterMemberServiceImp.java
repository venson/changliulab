package com.venson.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.venson.commonutils.JwtUtils;
import com.venson.educenter.entity.UcenterMember;
import com.venson.educenter.mapper.UcenterMemberMapper;
import com.venson.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
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
public class UcenterMemberServiceImp extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

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
    public String register(UcenterMember ucenterMember) {

        return null;
    }
}
