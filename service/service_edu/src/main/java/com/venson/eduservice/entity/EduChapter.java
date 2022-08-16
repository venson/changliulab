package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.venson.eduservice.entity.enums.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
@Getter
@Setter
@TableName("edu_chapter")
public class EduChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 章节ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 章节名称
     */
    private String title;

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

    /**
     * 显示排序
     */
    private Integer sort;

    private Boolean isRemoveAfterReview;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
