package com.venson.eduservice.entity.statemachine;

import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ChapterContext implements Serializable {
    @Serial
    private static final long serialVersionUID = 774596723457L;

    private EduChapter chapter;
    private ReviewApplyVo reviewVo;

}
