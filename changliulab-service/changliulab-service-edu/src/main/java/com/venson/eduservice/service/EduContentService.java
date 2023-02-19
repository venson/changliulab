package com.venson.eduservice.service;

import com.venson.eduservice.entity.dto.CourseSyllabusDTO;

import java.util.List;

public interface EduContentService {
    List<CourseSyllabusDTO> getSyllabusByCourseId(Long courseId);
}
