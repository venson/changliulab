package com.venson.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.front.dto.CourseFrontBriefDTO;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
public interface EduCoursePublishedMapper extends BaseMapper<EduCoursePublished> {


    List<CourseFrontBriefDTO> getFrontIndexCourse();
}
