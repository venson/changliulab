package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
@Getter
@Setter
@TableName("edu_section_published")
public class EduSectionPublished implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private Long id;

    private Long courseId;

    private Long chapterId;

    private String title;

    private String videoLink;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private String content;

    @Version
    private Long version;

    private Long viewCount;


    private Integer sort;


}
