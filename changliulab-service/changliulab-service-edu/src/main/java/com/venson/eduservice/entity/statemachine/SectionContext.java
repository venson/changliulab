package com.venson.eduservice.entity.statemachine;

import com.venson.eduservice.entity.EduSection;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SectionContext implements Serializable {
    @Serial
    private static final long serialVersionUID = 94587456436745967L;

    private EduSection section;
    private ReviewApplyVo reviewVo;

}
