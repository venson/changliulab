package com.venson.eduservice.entity.vo;

import lombok.Data;

@Data
public class ReviewApplyVo {
    private Long id;
    private String name;
    private Long courseId;
    private String msg;
}