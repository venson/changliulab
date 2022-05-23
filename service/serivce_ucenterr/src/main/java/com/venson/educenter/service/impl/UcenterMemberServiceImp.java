package com.venson.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.JwtUtils;
import com.venson.educenter.entity.UcenterMember;
import com.venson.educenter.mapper.UcenterMemberMapper;
import com.venson.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-05-23
 */
@Service
public class UcenterMemberServiceImp extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public String login(UcenterMember ucenterMember) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        if(ObjectUtils.isEmpty(mobile) || ObjectUtils.isEmpty(password)) {
           throw new CustomizedException(20001,"invalid username or password") ;
        }

        wrapper.eq("mobile",ucenterMember.getMobile());

        UcenterMember member = baseMapper.selectOne(wrapper);
        if(ObjectUtils.isEmpty(member.getPassword())){
            throw new CustomizedException(20001,"no such user");
        }

        if(!password.equals(member.getPassword())){
            throw new CustomizedException(20001,"invalid password");
        }
        if(member.getIsDisabled()){
            throw new CustomizedException(20001,"User is disabled");
        }

        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public boolean register(UcenterMember ucenterMember) {
        return false;
    }
}
