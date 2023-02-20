package com.venson.eduservice.entity.dto;

import com.venson.eduservice.entity.enums.ReviewStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Data
public class ActivityDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Title can not be empty")
    private String title;

    @NotBlank
    private String activityDate;

    private Long authorMemberId;

    private String authorMemberName;

    private Long lastModifiedMemberId;

    private Boolean isPublished;

    private Boolean isModified;

    private ReviewStatus review;

    private String htmlBrBase64;
    private String markdown;

    private Boolean isRemoveAfterReview;
}
