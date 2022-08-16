package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.enums.ReviewType;
import com.venson.eduservice.entity.vo.ReviewApplyVo;
import com.venson.eduservice.mapper.EduActivityMapper;
import com.venson.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        // check if the review is already applied
        wrapper.eq(EduReview::getRefType, ReviewType.ACTIVITY)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED)
                .eq(EduReview::getRefId, id);
        EduReview one = reviewService.getOne(wrapper);
        if(one != null) throw new CustomizedException(30000,"Review already Applied");

        // check if the review was rejected before
        wrapper.clear();
        wrapper.eq(EduReview::getRefType, ReviewType.ACTIVITY)
                .eq(EduReview::getStatus, ReviewStatus.REJECTED)
                .eq(EduReview::getRefId, id);
        EduReview review;
        review= reviewService.getOne(wrapper);
        if(review!=null){
            reviewService.setReviewStatus(review, ReviewStatus.APPLIED,reviewVo);
            reviewService.updateById(review);
        }else{
            review = new EduReview();
            reviewService.setReviewStatus(review, ReviewStatus.APPLIED,reviewVo);
            reviewService.save(review);
        }
        // update activity status
        LambdaUpdateWrapper<EduActivity> activityWrapper = new LambdaUpdateWrapper<>();
        activityWrapper.eq(EduActivity::getId, id)
                .set(EduActivity::getStatus, ReviewStatus.APPLIED);
        baseMapper.update(null,activityWrapper);
    }

    @Transactional
    @Override
    public void passReviewByActivityId(Long id, ReviewApplyVo reviewVo) {

        LambdaQueryWrapper<EduReview> reviewWrapper = new LambdaQueryWrapper<>();
        reviewWrapper.eq(EduReview::getRefType, ReviewType.ACTIVITY)
                .eq(EduReview::getStatus, ReviewStatus.APPLIED)
                .eq(EduReview::getRefId,id);
        EduReview review = reviewService.getOne(reviewWrapper);
        if(review == null) throw new CustomizedException(30000,"No Corresponding Review");

        reviewService.setReviewStatus(review,ReviewStatus.FINISHED,reviewVo);
        reviewService.updateById(review);
        // update activity

        EduActivity activity = baseMapper.selectById(id);
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
        EduActivityPublished activityPublished = activityPublishedService.getById(id);
        activityPublished.setIsPublished(false);
        activityPublishedService.updateById(activityPublished);
    }
}
