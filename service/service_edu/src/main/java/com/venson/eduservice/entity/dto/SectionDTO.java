package com.venson.eduservice.entity.dto;

import com.venson.eduservice.entity.EduSection;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper =false)
@Data
public class SectionDTO extends EduSection {
    private String markdown;
}
