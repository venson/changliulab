package com.venson.eduservice.entity.frontvo;

import lombok.Data;

@Data
public class CourseFrontFIlterVo {
    private Long id;
    private String title;
    private Long memberId;
    private String subjectParentId;
    private String subjectId;
    private Integer gmtCreateSort;
    private Integer viewSort;
    private Integer updateSort;

}
