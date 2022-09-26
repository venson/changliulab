package com.venson.eduservice.service.front.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.service.EduMemberScholarService;
import com.venson.eduservice.service.EduScholarService;
import com.venson.eduservice.service.front.ScholarFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScholarFrontServiceImpl implements ScholarFrontService {
    @Autowired
    private EduMemberScholarService memberScholarService;

    @Autowired
    private EduScholarService scholarService;

    @Override
    @Cacheable(value = "member:scholar",key = "#memberId + ':'+ #pageNum + ':'+ #limit")
    public Map<String, Object> getPageScholarByMemberId(Long memberId, Integer pageNum, Integer limit) {
        Page<EduScholar> page = new Page<>(pageNum, limit);
        List<String> scholarList = memberScholarService.getScholarListByMemberId(memberId);
        LambdaQueryWrapper<EduScholar> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(EduScholar::getId,scholarList);
        scholarService.page(page,wrapper);
        return PageUtil.toMap(page);
    }
}
