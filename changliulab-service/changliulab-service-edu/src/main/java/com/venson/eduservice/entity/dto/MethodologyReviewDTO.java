package com.venson.eduservice.entity.dto;

import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.entity.EduReview;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MethodologyReviewDTO extends EduMethodology {
    private List<EduReview> reviewList;
}
