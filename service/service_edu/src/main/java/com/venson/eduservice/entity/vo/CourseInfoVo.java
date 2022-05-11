package com.venson.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseInfoVo {

    @ApiModelProperty("the id of course")
    private String id;
    @ApiModelProperty("the title of teacher who is teaching the course")
    private String teacherId;
    @ApiModelProperty("the id of the course")
    private String subjectId;
    @ApiModelProperty("the title of the course")
    private String title;
    @ApiModelProperty("price of the course, 0 for free watch")
    private BigDecimal price;
    @ApiModelProperty("total time duration of the course")
    private Integer lessonNum;
    @ApiModelProperty("cover path of course")
    private String cover;
    @ApiModelProperty("course description")
    private String description;

}
