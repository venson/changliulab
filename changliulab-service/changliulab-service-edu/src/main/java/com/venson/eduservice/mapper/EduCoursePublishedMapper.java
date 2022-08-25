package com.venson.eduservice.mapper;

import com.venson.eduservice.entity.EduCoursePublished;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
public interface EduCoursePublishedMapper extends BaseMapper<EduCoursePublished> {

    CourseFrontInfoVo getFrontCourseInfo(Long id);
}
