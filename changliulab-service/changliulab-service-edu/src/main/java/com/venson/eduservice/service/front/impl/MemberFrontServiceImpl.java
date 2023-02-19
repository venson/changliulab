package com.venson.eduservice.service.front.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.enums.MemberLevel;
import com.venson.eduservice.entity.front.dto.MemberFrontBriefDTO;
import com.venson.eduservice.service.EduMemberService;
import com.venson.eduservice.service.front.MemberFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberFrontServiceImpl implements MemberFrontService {
    @Autowired
    private EduMemberService memberService;
    @Override
    @Cacheable(value = "member:PI")
    public List<EduMember> getPIMemberFront() {
        LambdaQueryWrapper<EduMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduMember::getLevel, MemberLevel.PI)
                .select(EduMember::getId,EduMember::getAvatar,
                        EduMember::getName,EduMember::getIntro,
                        EduMember::getTitle,EduMember::getCareer);
        return memberService.list(wrapper);
    }

    @Override
    @Cacheable(value = "member", key = "#level+':'+#page+':'+#limit")
    public PageResponse<EduMember> getPageFrontMemberListByLevel(Integer page, Integer limit, MemberLevel level) {
        LambdaQueryWrapper<EduMember> wrapper = new LambdaQueryWrapper<>();
//        switch (level) {
//            case "PI" -> wrapper.eq(EduMember::getLevel, MemberLevel.PI);
//            case "CM" -> wrapper.eq(EduMember::getLevel, MemberLevel.CURRENT_MEMBER);
//            case "FM" -> wrapper.eq(EduMember::getLevel, MemberLevel.FORMER_MEMBER);
//            case "IN" -> wrapper.eq(EduMember::getLevel, MemberLevel.INTERN);
//            default -> throw new CustomizedException(20001, "invalid level");
//        }
        wrapper.eq(EduMember::getLevel, level);
        wrapper.select(EduMember::getId,EduMember::getAvatar,
                        EduMember::getName,EduMember::getIntro,
                        EduMember::getTitle,EduMember::getCareer);
        Page<EduMember> memberPage = new Page<>(page,limit);
        memberService.page(memberPage,wrapper);
        return PageUtil.toBean(memberPage);
    }

    @Override
    @Cacheable(value = "member", key = "'Id:'+#id")
    public EduMember getMemberFrontByID(Long id) {
        return memberService.getById(id);

    }

    @Override
    public List<MemberFrontBriefDTO> getFrontIndexMember() {
        return memberService.getFrontIndexMember();
    }
}
