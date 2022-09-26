package com.venson.eduservice.service.front;

import com.venson.eduservice.entity.frontvo.CourseFrontInfoDTO;

import java.util.Map;

public interface CourseFrontService {
    CourseFrontInfoDTO getFrontCourseInfo(Long id);

    Map<String, Object> getPageCourseByMemberId(Long id, Integer page, Integer limit);
}
