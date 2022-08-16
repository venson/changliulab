package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.enums.ReviewType;
import com.venson.eduservice.mapper.EduResearchMapper;
import com.venson.eduservice.service.EduResearchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.eduservice.service.EduReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@Service
public class EduResearchServiceImp extends ServiceImpl<EduResearchMapper, EduResearch> implements EduResearchService {

    @Autowired
    private EduReviewService reviewService;


    @Override
    public List<EduReview> getReviewByResearchId(Long id) {
        LambdaQueryWrapper<EduReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduReview::getRefType, ReviewType.RESEARCH)
                .eq(EduReview::getRefId, id);
        return reviewService.list(wrapper);
    }

    @Override
    public List<EduResearch> getResearchReviewList() {
        LambdaQueryWrapper<EduResearch> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduResearch::getStatus, ReviewStatus.APPLIED);
        return baseMapper.selectList(wrapper);
    }
}
