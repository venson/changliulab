package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.dto.ReviewDTO;
import com.venson.eduservice.entity.enums.ReviewType;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-16
 */
public interface EduReviewService extends IService<EduReview> {




    List<EduReview> getReviewByResearchId(Long id);




    List<EduReview> getReviewByMethodologyId(Long id);


    List<ReviewDTO> getReviewListByRefId(Long id, ReviewType type);
}
