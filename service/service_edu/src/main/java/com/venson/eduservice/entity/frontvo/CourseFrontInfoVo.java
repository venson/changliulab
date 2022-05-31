package com.venson.eduservice.entity.frontvo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseFrontInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private Integer viewCount;
    private String description;
    private String teacherId;
    private String teacherName;
    private String intro;
    private String avatar;
    private String l1SubjectId;
    private String l2SubjectId;
    private String l1Subject;
    private String l2Subject;
    private Integer available;

}
