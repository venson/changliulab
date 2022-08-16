package com.venson.eduservice.entity.chapter;


import com.venson.eduservice.entity.enums.ReviewStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CourseTreeNodeVo {
    private Long id;
    private String title;
    private ReviewStatus review;
    private Boolean isPublished;
    private Boolean isModified;
    private Boolean isRemoveAfterReview;
    private List<CourseTreeNodeVo> children = new ArrayList<>();


    public CourseTreeNodeVo(Long id, String title, ReviewStatus review, Boolean isPublished, Boolean isModified, Boolean isRemoveAfterReview) {
        this.id = id;
        this.title = title;
        this.review = review;
        this.isPublished = isPublished;
        this.isModified = isModified;
        this.isRemoveAfterReview = isRemoveAfterReview;
    }
}
