package com.venson.eduservice.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseInfoVo {

    private Long id;
    private Long memberId;
    private Long subjectId;
    private Long subjectParentId;
    private String title;
    private BigDecimal price;
    private Integer totalHour;
    private String cover;
    private String description;

}
