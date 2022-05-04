package com.venson.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {
    @ApiModelProperty(value="name of teacher")
    private String name;
    @ApiModelProperty(value="level of teacher,1 Senior, 2 chief")
    private Integer level;
    @ApiModelProperty(value = "search by time start")
    private String begin;
    @ApiModelProperty(value = "search by time end")
    private String end;
}
