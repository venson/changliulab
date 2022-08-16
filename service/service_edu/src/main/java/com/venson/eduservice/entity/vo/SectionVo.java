package com.venson.eduservice.entity.vo;

import com.venson.eduservice.entity.EduSection;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper =false)
@Data
public class SectionVo extends EduSection {
    private String markdown;
}
