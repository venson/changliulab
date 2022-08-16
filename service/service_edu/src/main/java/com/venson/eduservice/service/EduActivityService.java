package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.vo.ReviewApplyVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
public interface EduActivityService extends IService<EduActivity> {

    Map<String, Object> getPageReviewList(Integer page, Integer limit);

    void requestReviewByActivityId(Long id, ReviewApplyVo reviewVo);

    void passReviewByActivityId(Long id, ReviewApplyVo reviewVo);
    void rejectReviewByActivityId(Long id, ReviewApplyVo reviewVo);

    void hideActivityById(Long id);
}
