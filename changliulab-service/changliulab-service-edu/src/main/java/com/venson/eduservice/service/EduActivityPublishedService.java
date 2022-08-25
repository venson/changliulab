package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduActivityPublished;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-08-09
 */
public interface EduActivityPublishedService extends IService<EduActivityPublished> {

    Map<String, Object> getPageActivityList(Integer page, Integer limit);
}
