package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduChapterPublished;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
public interface EduChapterPublishedService extends IService<EduChapterPublished> {

    void passReview(Long id);
}
