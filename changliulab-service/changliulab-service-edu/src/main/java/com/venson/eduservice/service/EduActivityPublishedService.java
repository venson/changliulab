package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduActivityPublished;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.front.dto.ActivityFrontBriefDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-08-09
 */
public interface EduActivityPublishedService extends IService<EduActivityPublished> {

//    PageResponse<EduActivityPublished> getPageActivityList(Integer page, Integer limit);

    List<ActivityFrontBriefDTO> getFrontIndexActivity();
}
