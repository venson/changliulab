package com.venson.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.dto.CourseInfoVo;
import com.venson.eduservice.entity.dto.CoursePreviewVo;

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

    Long addCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseById(Long id);

    void updateCourse(CourseInfoVo infoVo);

    void removeCourseById(Long courseId);


    Map<String, Object> getPageCoursePublishVo(Integer pageNum, Integer limit, String condition);

    CoursePreviewVo getCoursePreviewById(Long courseId);

    Map<String, Object> getPageReviewCourse(Integer pageNum, Integer limit, LambdaQueryWrapper<EduCourse> courseWrapper);

    void actualRemoveCourseById(Long courseId);

    Long addEmptyCourse();

}
