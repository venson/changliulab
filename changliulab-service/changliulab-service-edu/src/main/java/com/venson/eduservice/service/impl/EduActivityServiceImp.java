package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduActivity;
import com.venson.eduservice.entity.EduActivityMarkdown;
import com.venson.eduservice.entity.EduActivityPublished;
import com.venson.eduservice.entity.EduActivityPublishedMd;
import com.venson.eduservice.entity.dto.ActivityDTO;
import com.venson.eduservice.entity.dto.ActivityPreviewDTO;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.mapper.EduActivityMapper;
import com.venson.eduservice.service.EduActivityMarkdownService;
import com.venson.eduservice.service.EduActivityPublishedMdService;
import com.venson.eduservice.service.EduActivityPublishedService;
import com.venson.eduservice.service.EduActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@Service
@Slf4j
public class EduActivityServiceImp extends ServiceImpl<EduActivityMapper, EduActivity> implements EduActivityService {

    @Autowired
    private EduActivityMarkdownService activityMarkdownService;

    @Autowired
    private EduActivityPublishedService publishedService;
    @Autowired
    private EduActivityPublishedMdService publishedMdService;

    @Override
    public PageResponse<EduActivity> getPageReviewList(Integer page, Integer limit) {
        Page<EduActivity> reviewPage = new Page<>(page, limit);
        LambdaQueryWrapper<EduActivity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduActivity::getReview, ReviewStatus.APPLIED);
        baseMapper.selectPage(reviewPage, wrapper);
        return PageUtil.toBean(reviewPage);
    }


    @Override
    public void switchEnableByActivityId(Long id) {
        EduActivity activity = baseMapper.selectById(id);
        Assert.notNull(activity, "Activity not exist");
        activity.setEnable(!activity.getEnable());
        baseMapper.updateById(activity);
        EduActivityPublished published = publishedService.getById(id);
        if (published != null) {
            published.setEnable(activity.getEnable());
            publishedService.updateById(published);
        }
    }

    @Override
    public PageResponse<EduActivity> getPageActivityList(Integer page, Integer limit, String title, String begin, String end) {
        LambdaQueryWrapper<EduActivity> wrapper = new QueryWrapper<EduActivity>().lambda();
        Page<EduActivity> pageActivity = new Page<>(page, limit);
        log.info("begin");
        log.info(begin);
        log.info("end");
        log.info(end);
        boolean beginEnable = StringUtils.hasText(begin);
        boolean endEnable = StringUtils.hasText(end);
        wrapper.select(EduActivity::getId, EduActivity::getTitle, EduActivity::getActivityDate, EduActivity::getAuthorMemberName
                , EduActivity::getIsModified, EduActivity::getIsPublished, EduActivity::getReview);

        wrapper.like(StringUtils.hasText(title), EduActivity::getTitle, title);
        wrapper.ge(beginEnable, EduActivity::getGmtCreate, begin);
        wrapper.le(endEnable, EduActivity::getGmtCreate, end);
        wrapper.orderByDesc(EduActivity::getGmtCreate);
        baseMapper.selectPage(pageActivity, wrapper);
        return PageUtil.toBean(pageActivity);
    }


    @Transactional
    @Override
    public void updateActivity(Long id, ActivityDTO activityDTO) {
        EduActivity activity = baseMapper.selectById(id);
        Assert.notNull(activity, "Activity not exits");
        Assert.isTrue(activity.getReview() != ReviewStatus.APPLIED, "Activity is under Review");
        copyActivityBean(activityDTO, activity);

//        BeanUtils.copyProperties(activityDTO, activity);
        activity.setIsModified(true);
        baseMapper.updateById(activity);

        EduActivityMarkdown markdown = activityMarkdownService.getById(id);
        markdown.setMarkdown(activityDTO.getMarkdown());
        markdown.setHtmlBrBase64(activityDTO.getHtmlBrBase64());
        activityMarkdownService.updateById(markdown);
    }

    @Override
    @Transactional
    public void deleteActivity(Long id) {
        EduActivity activity = baseMapper.selectById(id);
        if (activity.getReview() == null || activity.getReview() == ReviewStatus.NONE) {
            baseMapper.deleteById(id);
            activityMarkdownService.removeById(id);
        } else {
            activity.setIsRemoveAfterReview(true);
            baseMapper.updateById(activity);
        }
    }


    @Override
    public ActivityDTO getActivityById(Long id) {

        EduActivity eduActivity = baseMapper.selectById(id);
        EduActivityMarkdown markdown = activityMarkdownService.getById(id);
        ActivityDTO activity = new ActivityDTO();
        BeanUtils.copyProperties(eduActivity, activity);
        activity.setMarkdown(markdown.getMarkdown());
        return activity;
    }

    @Override
    public ActivityPreviewDTO getPreviewByActivityId(long id) {
        EduActivity activity = baseMapper.selectById(id);
        ActivityPreviewDTO preview = new ActivityPreviewDTO();
        BeanUtils.copyProperties(activity, preview);
        EduActivityMarkdown markdown = activityMarkdownService.getById(id);
        EduActivityPublishedMd publishedMd = publishedMdService.getById(id);

        preview.setHtmlBrBase64(markdown.getHtmlBrBase64());
        if (!ObjectUtils.isEmpty(publishedMd)) preview.setPublishedHtmlBrBase64(publishedMd.getHtmlBrBase64());
        return preview;
    }

    @Override
    public Long addActivity(ActivityDTO activityDTO) {
        EduActivity eduActivity = new EduActivity();
        EduActivityMarkdown eduActivityMarkdown = new EduActivityMarkdown();
        copyActivityBean(activityDTO, eduActivity);
        eduActivity.setReview(ReviewStatus.NONE);
        baseMapper.insert(eduActivity);
//        eduActivityMarkdown.setMarkdown(activityDTO.getMarkdown());
//        eduActivityMarkdown.setHtmlBrBase64(activityDTO.getHtmlBrBase64());
        eduActivityMarkdown.setId(eduActivity.getId());
        activityMarkdownService.save(eduActivityMarkdown);
        return eduActivity.getId();
    }

    private void copyActivityBean(ActivityDTO source, EduActivity target) {
        target.setTitle(source.getTitle());
        target.setActivityDate(source.getActivityDate());
        target.setAuthorMemberId(source.getAuthorMemberId());
        target.setAuthorMemberName(source.getAuthorMemberName());

    }
}
