package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.enums.ReviewType;
import com.venson.eduservice.entity.dto.ActivityInfoVo;
import com.venson.eduservice.entity.dto.ActivityQuery;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.mapper.EduActivityMapper;
import com.venson.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@Service
@Slf4j
public class EduActivityServiceImp extends ServiceImpl<EduActivityMapper, EduActivity> implements EduActivityService {

    @Autowired
    private EduReviewService reviewService;
    @Autowired
    private EduActivityMarkdownService activityMarkdownService;
    @Autowired
    private EduActivityPublishedService activityPublishedService;
    @Autowired
    private EduActivityPublishedMdService  activityPublishedMdService;

    @Override
    public Map<String, Object> getPageReviewList(Integer page, Integer limit) {
        Page<EduActivity> reviewPage = new Page<>(page, limit);
        LambdaQueryWrapper<EduActivity> wrapper= new LambdaQueryWrapper<>();
        wrapper.eq(EduActivity::getStatus, ReviewStatus.APPLIED);
        baseMapper.selectPage(reviewPage,wrapper);
        return PageUtil.toMap(reviewPage);
    }

    @Transactional
    @Override
    public void requestReviewByActivityId(Long id, ReviewApplyVo reviewVo) {
        EduActivity activity = baseMapper.selectById(id);
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        // check if the review is already applied
        if(activity.getStatus()==ReviewStatus.APPLIED){
            throw new CustomizedException(30000,"Review already Applied");
        }
        EduReview review;
        // check if the review was rejected before
        if(activity.getStatus()==ReviewStatus.REJECTED){
            wrapper.eq(EduReview::getRefType, ReviewType.ACTIVITY)
                    .eq(EduReview::getStatus, ReviewStatus.REJECTED)
                    .eq(EduReview::getRefId, id);
            review= reviewService.getOne(wrapper);
            reviewService.setReviewStatus(review, ReviewStatus.APPLIED,reviewVo);
            log.info(review.toString());
            reviewService.save(review);
        }else{
            review = new EduReview();
            review.setRefType(ReviewType.ACTIVITY);
            review.setRefId(id);
            reviewService.setReviewStatus(review, ReviewStatus.APPLIED,reviewVo);
        }
        reviewService.saveOrUpdate(review);

        // update activity status
        activity.setStatus(ReviewStatus.APPLIED);
        baseMapper.updateById(activity);
    }

    @Transactional
    @Override
    public void passReviewByActivityId(Long id, ReviewApplyVo reviewVo) {
        EduActivity activity = baseMapper.selectById(id);
        if(activity.getStatus()!= ReviewStatus.APPLIED){
            throw new CustomizedException(30000,"No Corresponding Review");
        }

        LambdaQueryWrapper<EduReview> reviewWrapper = new LambdaQueryWrapper<>();
        reviewWrapper.eq(EduReview::getRefType, ReviewType.ACTIVITY)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED)
                .eq(EduReview::getRefId,id);
        EduReview review = reviewService.getOne(reviewWrapper);

        reviewService.setReviewStatus(review,ReviewStatus.FINISHED,reviewVo);
        reviewService.updateById(review);
        // update activity

        activity.setIsPublished(true);
        activity.setIsModified(false);
        activity.setStatus(ReviewStatus.FINISHED);
        baseMapper.updateById(activity);

        // save activity to edu_activity_published

        EduActivityPublished activityPublished = new EduActivityPublished();
        BeanUtils.copyProperties(activity,activityPublished);
        activityPublishedService.saveOrUpdate(activityPublished);

        EduActivityMarkdown markdown = activityMarkdownService.getById(id);
        EduActivityPublishedMd publishedMd = new EduActivityPublishedMd();
        BeanUtils.copyProperties(markdown,publishedMd);
        activityPublishedMdService.saveOrUpdate(publishedMd);


    }

    @Transactional
    @Override
    public void rejectReviewByActivityId(Long id, ReviewApplyVo reviewVo) {
        EduActivity activity = baseMapper.selectById(id);
        activity.setStatus(ReviewStatus.REJECTED);
        baseMapper.updateById(activity);

        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.ACTIVITY)
                .eq(EduReview::getRefId,id)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED);
        EduReview review = reviewService.getOne(wrapper);
        reviewService.setReviewStatus(review,ReviewStatus.REJECTED,reviewVo);
        reviewService.updateById(review);
    }

    @Override
    public void hideActivityById(Long id) {
        EduActivity activity = baseMapper.selectById(id);
        activity.setIsPublished(false);
        activity.setIsModified(true);
        baseMapper.updateById(activity);
    }

    @Override
    public Map<String, Object> getPageActivityList(Integer page, Integer limit, ActivityQuery query) {
        LambdaQueryWrapper<EduActivity> wrapper = new QueryWrapper<EduActivity>().lambda();
        Page<EduActivity> pageActivity = new Page<>(page, limit);
        String title = query.getTitle();
        String begin = query.getBegin();
        String end = query.getEnd();

        if(!ObjectUtils.isEmpty(title)){
            wrapper.like(EduActivity::getTitle,title);
        }
        if(!ObjectUtils.isEmpty(begin)){
            wrapper.ge(EduActivity::getGmtCreate,begin);
        }
        if(!ObjectUtils.isEmpty(end)){
            wrapper.le(EduActivity::getGmtCreate,end);
        }
        wrapper.orderByDesc(EduActivity::getGmtCreate);
        baseMapper.selectPage(pageActivity,wrapper);
        return PageUtil.toMap(pageActivity);
    }

    @Override
    @Transactional
    public Long saveActivity(ActivityInfoVo infoVo) {
        EduActivity eduActivity = new EduActivity();
        BeanUtils.copyProperties(infoVo,eduActivity);
        eduActivity.setIsModified(true);
        eduActivity.setIsPublished(null);
        baseMapper.insert(eduActivity);
        EduActivityMarkdown markdown = new EduActivityMarkdown();
        markdown.setId(eduActivity.getId());
        markdown.setMarkdown(infoVo.getMarkdown());
        activityMarkdownService.save(markdown);
        return eduActivity.getId();
    }

    @Transactional
    @Override
    public void updateActivity(Long id, ActivityInfoVo infoVo) {
        EduActivity activity =baseMapper.selectById(id);
        if(activity == null){
            throw new CustomizedException(30000,"The activity is not available");
        }
        if(activity.getStatus()== ReviewStatus.APPLIED){
            throw new CustomizedException(30000,"The activity is under review");
        }

        BeanUtils.copyProperties(infoVo,activity);
        activity.setIsModified(true);
        baseMapper.updateById(activity);

        EduActivityMarkdown markdown =activityMarkdownService.getById(id);
        markdown.setMarkdown(infoVo.getMarkdown());
        activityMarkdownService.updateById(markdown);
    }

    @Override
    @Transactional
    public void deleteActivity(Long id) {
        EduActivity activity = baseMapper.selectById(id);
        if(activity.getStatus()==ReviewStatus.NONE){
            baseMapper.deleteById(id);
            activityMarkdownService.removeById(id);
        }else{
            activity.setIsRemoveAfterReview(true);
            baseMapper.updateById(activity);
        }
    }
}
