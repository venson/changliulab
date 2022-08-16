package com.venson.eduservice.entity.vo;

import com.venson.eduservice.entity.enums.ReviewStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePreviewVo implements Serializable {
    private static final  long serialVersionUID = 22123323L;
    private Long id;
    private String title;
    private Integer totalHour;
    private String courseDescription;
    private String memberName;
    private Integer viewCount;
    private Boolean isModified;
    private ReviewStatus review;
    private Boolean isPublished;
    private String cover;
    private String topSubject;
    private String levelISubject;
}