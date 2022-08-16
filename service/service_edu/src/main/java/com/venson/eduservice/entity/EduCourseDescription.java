package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 课程简介
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@TableName("edu_course_description")
@Data
public class EduCourseDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT,value = "id")
    private Long id;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


    @Override
    public String toString() {
        return "EduCourseDescription{" +
        "id=" + id +
        ", description=" + description +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        "}";
    }
}
