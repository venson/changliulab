package com.venson.eduservice.entity.vo;

import com.venson.eduservice.entity.EduActivity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityInfoVo extends EduActivity{
    private String markdown;
}
