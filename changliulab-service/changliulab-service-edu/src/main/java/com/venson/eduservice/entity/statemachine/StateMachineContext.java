package com.venson.eduservice.entity.statemachine;

import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateMachineContext implements Serializable {
    @Serial
    private static final long serialVersionUID = 237723744596723457L;

    private EduReview review;
    private ReviewApplyVo reviewVo;

}
