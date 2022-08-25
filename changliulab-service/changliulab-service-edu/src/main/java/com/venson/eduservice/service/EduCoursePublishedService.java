package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.eduservice.entity.EduCoursePublished;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.frontvo.CourseFrontFIlterVo;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.venson.eduservice.entity.frontvo.CourseFrontTreeNodeVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
public interface EduCoursePublishedService extends IService<EduCoursePublished> {

    Map<String, Object> getFrontPageCourseList(Integer page, Integer limit, CourseFrontFIlterVo courseFrontVo);

    CourseFrontInfoVo getFrontCourseInfo(Long id);

    List<CourseFrontTreeNodeVo> getCourseFrontTreeByCourseId(Long id);
}
