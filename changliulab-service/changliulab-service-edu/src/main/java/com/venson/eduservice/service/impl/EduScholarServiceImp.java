package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.EduScholarCitation;
import com.venson.eduservice.entity.dto.ScholarAdminDTO;
import com.venson.eduservice.entity.vo.ScholarFilterVo;
import com.venson.eduservice.mapper.EduScholarMapper;
import com.venson.eduservice.service.EduMemberScholarService;
import com.venson.eduservice.service.EduScholarCitationService;
import com.venson.eduservice.service.EduScholarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-06-18
 */
@Service
public class EduScholarServiceImp extends ServiceImpl<EduScholarMapper, EduScholar> implements EduScholarService {
    @Autowired
    private EduMemberScholarService memberScholarService;

    @Autowired
    private EduScholarCitationService scholarCitationService;


    @Override
    public PageResponse<EduScholar> getPageScholar(Integer page, Integer limit, ScholarFilterVo filterVo) {
        Page<EduScholar> pageScholar = new Page<>(page,limit);
        LambdaQueryWrapper<EduScholar> wrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtils.isEmpty(filterVo)){
            wrapper.eq(StringUtils.hasText(filterVo.getYear()),EduScholar::getYear, filterVo.getYear());
            wrapper.like(StringUtils.hasText(filterVo.getAuthors()),EduScholar::getAuthors, filterVo.getAuthors());
            wrapper.like(StringUtils.hasText(filterVo.getTitle()),EduScholar::getTitle, filterVo.getTitle());
        }
        baseMapper.selectPage(pageScholar,wrapper);

        return PageUtil.toBean(pageScholar);
    }

    @Override
    public ScholarAdminDTO getScholarById(Long id) {
        EduScholar scholar = baseMapper.selectById(id);
        List<Long> memberList = memberScholarService.getMemberIdsByScholarId(id);
        List<EduScholarCitation>  citationList = scholarCitationService.getCitationsByScholarId(id);

        ScholarAdminDTO scholarAdminDTO = new ScholarAdminDTO();
        BeanUtils.copyProperties(scholar, scholarAdminDTO);
        scholarAdminDTO.setMemberIdList(memberList);
        scholarAdminDTO.setCitationList(citationList);
        return scholarAdminDTO;
    }

    @Override
    public void updateScholar(ScholarAdminDTO scholar) {
        Long scholarId = scholar.getId();
        EduScholar eduScholar = baseMapper.selectById(scholarId);
        Assert.notNull(eduScholar,"Scholar Not exist");
        BeanUtils.copyProperties(scholar, eduScholar);
        baseMapper.updateById(eduScholar);
        scholarCitationService.updateScholarCitation(scholar.getCitationList(),scholarId);
        memberScholarService.updateMemberScholarByMemberIdList(scholarId,scholar.getMemberIdList(),scholar.getMemberList());

    }

}
