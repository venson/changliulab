package com.venson.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePublishVo implements Serializable {
    private static final  long serialVersionUID = 2L;
    private String id;
    private String title;
    private String cover;
    private String courseDescription;
    private Integer lessonNum;
    private String topSubject;
    private String levelISubject;
    private String teacherName;
    private String price;
    private String status;
}
