package com.venson.eduservice.entity.frontvo;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
public class CourseFrontInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1938234L;

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
