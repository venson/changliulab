package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.dto.MemberQuery;
import com.venson.eduservice.entity.enums.MemberLevel;
import com.venson.eduservice.entity.front.dto.MemberFrontBriefDTO;
import com.venson.eduservice.entity.vo.MemberVo;
import com.venson.eduservice.mapper.EduMemberMapper;
import com.venson.eduservice.service.EduMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

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
    public PageResponse<EduMember> getMemberPage(Integer pageNum, Integer limit, MemberQuery memberQuery) {

        Page<EduMember> pageMember = new Page<>(pageNum,limit);
        LambdaQueryWrapper<EduMember> wrapper = new QueryWrapper<EduMember>().lambda();
        if(memberQuery!=null){
            String name = memberQuery.getName();
            MemberLevel level = memberQuery.getLevel();
            String begin = memberQuery.getBegin();
            String end = memberQuery.getEnd();
            if(!ObjectUtils.isEmpty(name)){
                wrapper.like(EduMember::getName,name);
            }
            if(!ObjectUtils.isEmpty(level)){
                wrapper.eq(EduMember::getLevel,level.getValue());
            }
            if(!ObjectUtils.isEmpty(begin)){
                wrapper.ge(EduMember::getGmtCreate, begin);
            }
            if(!ObjectUtils.isEmpty(end)){
                wrapper.le(EduMember::getGmtCreate, end);
            }
        }

        wrapper.orderByDesc(EduMember::getId);
        baseMapper.selectPage(pageMember,wrapper);
        return PageUtil.toBean(pageMember);

    }

    @Override
    public void updateMember(Long id, MemberVo member) {
        EduMember eduMember =baseMapper.selectById(id);
        BeanUtils.copyProperties(member, eduMember);
        baseMapper.updateById(eduMember);
    }

    @Override
    public List<MemberFrontBriefDTO> getFrontIndexMember() {
        return baseMapper.getFrontIndexMember();
    }

    @Override
    public List<EduMember> getCurrentMember() {
        LambdaQueryWrapper<EduMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduMember::getId,EduMember::getName, EduMember::getLevel);
        wrapper.ne(EduMember::getLevel, MemberLevel.FORMER_MEMBER).ne(EduMember::getLevel,MemberLevel.TECH);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<EduMember> getAllMember() {
        LambdaQueryWrapper<EduMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduMember::getId,EduMember::getName, EduMember::getLevel);
        wrapper.ne(EduMember::getLevel,MemberLevel.TECH);
        return baseMapper.selectList(wrapper);
    }
}
