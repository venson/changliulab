package com.venson.eduservice.entity.frontvo;

import lombok.Data;

@Data
public class CourseFrontFIlterVo {
    private String id;
    private String title;
    private String teacherId;
    private String subjectParentId;
    private String subjectId;
    private Integer gmtCreateSort;
    private Integer viewSort;
    private Integer updateSort;

}
