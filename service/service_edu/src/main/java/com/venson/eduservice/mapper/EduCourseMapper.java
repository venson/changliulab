package com.venson.eduservice.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.venson.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.venson.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
//    CoursePublishVo getPublishCourseInfo(String courseId);

    List<CoursePublishVo> getPublishCourseInfo(@Param(Constants.WRAPPER) Wrapper<CoursePublishVo> wrapper);
    CoursePublishVo getPublishCourseInfoById(String id);
}
