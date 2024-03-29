package com.venson.eduservice.entity.front.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ActivityFrontDTO implements Serializable {
    @Serial
    private static final long serialVersionUID=29384987347L;
    private Long id;
    private String title;
    private String markdown;
    private String html;
    private String activityDate;
    private String authorMemberName;
}
