package com.venson.eduservice.service.front.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoDTO;
import com.venson.eduservice.service.EduCoursePublishedService;
import com.venson.eduservice.service.front.CourseFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CourseFrontServiceImpl implements CourseFrontService {
    @Autowired
    private EduCoursePublishedService coursePublishedService;
    @Override
    @Cacheable(value = "courseFrontInfo",key = "#id")
    public CourseFrontInfoDTO getFrontCourseInfo(Long id) {
        return coursePublishedService.getFrontCourseInfo(id);
    }

    @Override
    @Cacheable(value = "member:course",key = "#id +':'+#page+':'+#limit")
    public Map<String, Object> getPageCourseByMemberId(Long id, Integer page, Integer limit) {
        Page<EduCoursePublished> coursePage= new Page<>(page, limit);
        LambdaQueryWrapper<EduCoursePublished> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduCoursePublished::getMemberId,id);
        coursePublishedService.page(coursePage,wrapper);
        return PageUtil.toMap(coursePage);
    }
}
