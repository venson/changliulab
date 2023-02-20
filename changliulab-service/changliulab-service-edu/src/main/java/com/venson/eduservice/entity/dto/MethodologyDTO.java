package com.venson.eduservice.entity.dto;

import com.venson.eduservice.entity.enums.LanguageEnum;
import com.venson.eduservice.entity.enums.ReviewStatus;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
public class MethodologyDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2378435671827346123L;

    private Long id;

    @NotBlank
    private String markdown;
    @NotBlank
    private String title;

    private String publishedMd;
    @NotBlank
    private String htmlBrBase64;
    private String publishedHtmlBrBase64;

    private LanguageEnum language;

    private Boolean isModified;

    private ReviewStatus review;

    private Boolean isPublished;
}
