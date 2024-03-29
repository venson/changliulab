package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.dto.CourseInfoDTO;
import com.venson.eduservice.entity.dto.CoursePageDTO;
import com.venson.eduservice.entity.dto.CoursePreviewVo;


/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
public interface EduCourseService extends IService<EduCourse> {

    Long addCourse(CourseInfoDTO courseInfoDTO);

    CourseInfoDTO getCourseById(Long id);

    void updateCourse(CourseInfoDTO infoVo);

    void setRemoveMarkByCourseById(Long courseId);


    PageResponse<EduCourse> getPageCoursePublishVo(Integer pageNum, Integer limit, String condition);

    CoursePreviewVo getCoursePreviewById(Long courseId);


    PageResponse<CoursePageDTO> getCoursePageReview(Integer current, Integer size);

    PageResponse<CoursePageDTO> getCoursePage(Integer current, Integer size, String condition);
}
