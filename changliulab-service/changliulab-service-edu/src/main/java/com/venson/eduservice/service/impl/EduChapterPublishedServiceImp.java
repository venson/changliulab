package com.venson.eduservice.service.impl;

import com.venson.eduservice.entity.EduChapterPublished;
import com.venson.eduservice.mapper.EduChapterPublishedMapper;
import com.venson.eduservice.service.EduChapterPublishedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
@Service
public class EduChapterPublishedServiceImp extends ServiceImpl<EduChapterPublishedMapper, EduChapterPublished> implements EduChapterPublishedService {

    @Override
    public void passReview(Long id) {
        EduChapterPublished chapter = baseMapper.selectById(id);
    }
}
