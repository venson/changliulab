package com.venson.eduservice.entity.statemachine;

import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CourseContext implements Serializable {
    @Serial
    private static final long serialVersionUID = 23774596723457L;

    private EduCourse course;
    private ReviewApplyVo reviewVo;

}
