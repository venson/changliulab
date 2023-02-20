package com.venson.eduservice.entity.dto;

import com.venson.eduservice.entity.EduReviewMsg;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.enums.ReviewType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7936434L;
    private Long id;
    private ReviewStatus status;
    private ReviewType refType;
    private Long refId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private List<EduReviewMsg > messages;
}
