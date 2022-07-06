package com.venson.eduservice.entity.frontvo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CourseFrontInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private Integer viewCount;
    private String description;
    private String memberId;
    private String memberName;
    private String intro;
    private String avatar;
    private String l1SubjectId;
    private String l2SubjectId;
    private String l1Subject;
    private String l2Subject;
    private Integer available;

}
