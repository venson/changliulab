package com.venson.eduservice.entity.frontvo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CourseFrontInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String cover;
    private Integer total_hour;
    private Integer viewCount;
    private String description;
    private Long memberId;
    private String memberName;
    private String intro;
    private String avatar;
    private Long l1SubjectId;
    private Long l2SubjectId;
    private String l1Subject;
    private String l2Subject;
    private Integer isPublic;

}
