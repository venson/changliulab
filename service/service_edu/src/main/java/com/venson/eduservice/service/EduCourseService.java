package com.venson.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.frontvo.CourseFrontFIlterVo;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.venson.eduservice.entity.vo.CourseInfoVo;
import com.venson.eduservice.entity.vo.CoursePreviewVo;
import org.apache.ibatis.annotations.Param;

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

    Map<String, Object> getPageReviewCoursePreviewVo(Integer pageNum, Integer limit, QueryWrapper<CoursePreviewVo> courseWrapper);

    void actualRemoveCourseById(Long courseId);
}
