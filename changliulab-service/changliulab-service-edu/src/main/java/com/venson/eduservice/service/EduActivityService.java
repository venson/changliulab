package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduActivity;
import com.venson.eduservice.entity.dto.ActivityDTO;
import com.venson.eduservice.entity.dto.ActivityPreviewDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
public interface EduActivityService extends IService<EduActivity> {

    PageResponse<EduActivity> getPageReviewList(Integer page, Integer limit);


    void switchEnableByActivityId(Long id);

    PageResponse<EduActivity> getPageActivityList(Integer page, Integer limit,String title,String begin, String end);


    void updateActivity(Long id, ActivityDTO infoVo);

    void deleteActivity(Long id);


    ActivityDTO getActivityById(Long id);

    ActivityPreviewDTO getPreviewByActivityId(long id);

    Long addActivity(ActivityDTO activityDTO);
}
