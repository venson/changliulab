package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.enums.MemberLevel;
import com.venson.eduservice.mapper.EduMemberMapper;
import com.venson.eduservice.service.EduMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-02
 */
@Service
public class EduMemberServiceImp extends ServiceImpl<EduMemberMapper, EduMember> implements EduMemberService {

    @Override
    public Map<String, Object> getMemberFrontList(Page<EduMember> memberPage) {
        QueryWrapper<EduMember> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(memberPage,wrapper);

        return PageUtil.toMap(memberPage);
    }

    @Override
    public Map<String, Object> getPageFrontMemberList(Integer page, Integer limit, String level) {
        LambdaQueryWrapper<EduMember> wrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtils.isEmpty(level)){
            switch(level){
                case "PI":
                    wrapper.eq(EduMember::getLevel, MemberLevel.PI);
                    break;
                case "CM":
                    wrapper.eq(EduMember::getLevel, MemberLevel.CURRENT_MEMBER);
                    break;
                case "FM":
                    wrapper.eq(EduMember::getLevel, MemberLevel.FORMER_MEMBER);
                case "IN":
                    wrapper.eq(EduMember::getLevel,MemberLevel.INTERN);
            }

        }
        Page<EduMember> memberPage = new Page<>(page,limit);
        baseMapper.selectPage(memberPage,wrapper);
        return PageUtil.toMap(memberPage);

    }
}
