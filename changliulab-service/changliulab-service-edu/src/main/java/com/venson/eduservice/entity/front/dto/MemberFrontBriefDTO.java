package com.venson.eduservice.entity.front.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MemberFrontBriefDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 239848587348L;
    private Long id;
    private String name;
    private String avatar;
    private String title;
    private String intro;
}
