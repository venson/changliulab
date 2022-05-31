package com.venson.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.frontvo.CourseFrontFIlterVo;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.venson.eduservice.entity.vo.CourseInfoVo;
import com.venson.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;


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

    CourseInfoVo getCourseInfo(String id);

    void updateCourseInfo(CourseInfoVo infoVo);



    IPage<CoursePublishVo> selectPageVo(IPage<CoursePublishVo> page, Wrapper<CoursePublishVo> wrapper);

    CoursePublishVo getPublishCourseInfoById(String id);

    void removeCourseById(String courseId);

    Map<String, Object> getFrontPageCourseList(Page<EduCourse> pageCourse, CourseFrontFIlterVo courseFrontVo);

    CourseFrontInfoVo getFrontCourseInfo(String id);
}
