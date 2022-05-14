package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.eduservice.entity.EduVideo;
import com.venson.eduservice.mapper.EduVideoMapper;
import com.venson.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@Service
public class EduVideoServiceImp extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public void removeVideoByCourseId(String courseId) {
//        EduVideo eduVideo = new EduVideo();
//        eduVideo.setCourseId(courseId);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
//        baseMapper.deleteById(eduVideo);
        baseMapper.delete(wrapper);
    }
}
