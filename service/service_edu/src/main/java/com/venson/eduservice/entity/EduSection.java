package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.venson.eduservice.entity.enums.ReviewStatus;
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
@TableName("edu_section")
public class EduSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long courseId;

    private Long chapterId;

    private String title;

    private String videoLink;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


    @Version
    private Long version;

    private Long viewCount;


    private Integer sort;
    private Boolean isRemoveAfterReview;

    /**
     * 课程状态 0未发布  1已发布
     */
    private Boolean isPublished;

    /**
     *  1（true）modified since publish， 0（false）not modified since publish
     */
    private Boolean isModified;

    /**
     * 0- , 1 request for review , 2 review request rejected
     */
    private ReviewStatus review;


}
