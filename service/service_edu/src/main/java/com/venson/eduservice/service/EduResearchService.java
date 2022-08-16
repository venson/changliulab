package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduResearch;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduReview;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
public interface EduResearchService extends IService<EduResearch> {

    List<EduReview> getReviewByResearchId(Long id);

    List<EduResearch> getResearchReviewList();
}
