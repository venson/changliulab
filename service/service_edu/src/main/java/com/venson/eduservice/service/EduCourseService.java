package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
