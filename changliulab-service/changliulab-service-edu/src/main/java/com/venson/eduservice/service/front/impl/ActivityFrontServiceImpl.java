package com.venson.eduservice.service.front.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduActivityPublished;
import com.venson.eduservice.entity.EduActivityPublishedMd;
import com.venson.eduservice.entity.front.dto.ActivityFrontBriefDTO;
import com.venson.eduservice.entity.front.dto.ActivityFrontDTO;
import com.venson.eduservice.service.EduActivityPublishedMdService;
import com.venson.eduservice.service.EduActivityPublishedService;
import com.venson.eduservice.service.front.ActivityFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityFrontServiceImpl implements ActivityFrontService {
    @Autowired
    private EduActivityPublishedService activityPublishedService;
    @Autowired
    private EduActivityPublishedMdService activityPublishedMdService;
    @Override
    @Cacheable(value = "activity:page",key = "'page:'+#page+'limit:'+#limit")
    public PageResponse<EduActivityPublished> getPageActivity(Integer page, Integer limit) {
        LambdaQueryWrapper<EduActivityPublished> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduActivityPublished::getId,EduActivityPublished::getTitle,
                EduActivityPublished::getAuthorMemberName,EduActivityPublished::getActivityDate);
        Page<EduActivityPublished> pageActivity = new Page<>(page, limit);
        activityPublishedService.page(pageActivity,wrapper);
        return PageUtil.toBean(pageActivity);
    }

    @Override
    @Cacheable(value = "activity",key = "'id:'+#id")
    public ActivityFrontDTO getActivityById(Long id) {
        EduActivityPublished eduActivity = activityPublishedService.getById(id);
        EduActivityPublishedMd eduActivityMd = activityPublishedMdService.getById(id);
        ActivityFrontDTO activity = new ActivityFrontDTO();
        activity.setActivityDate(eduActivity.getActivityDate());
        activity.setId(id);
        activity.setTitle(eduActivity.getTitle());
        activity.setAuthorMemberName(activity.getAuthorMemberName());
        activity.setMarkdown(eduActivityMd.getMarkdown());
        return activity;
    }

    @Override
    public List<ActivityFrontBriefDTO> getFrontIndexActivity() {
        return activityPublishedService.getFrontIndexActivity();
    }
}
