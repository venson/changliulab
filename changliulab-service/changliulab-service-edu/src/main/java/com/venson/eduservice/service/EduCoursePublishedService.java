package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.front.dto.CourseFrontBriefDTO;
import com.venson.eduservice.entity.front.dto.CourseSyllabusFrontDTO;
import com.venson.eduservice.entity.front.vo.CourseFrontFilterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
public interface EduCoursePublishedService extends IService<EduCoursePublished> {

    PageResponse<EduCoursePublished> getFrontPageCourseList(Integer page, Integer limit, CourseFrontFilterVo courseFrontVo);



    List<CourseFrontBriefDTO> getFrontIndexCourse();

    List<CourseSyllabusFrontDTO> getSyllabusByCourseId(Long id);
}
