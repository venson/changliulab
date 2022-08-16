package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.util.Date;

import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.enums.ReviewType;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author venson
 * @since 2022-07-16
 */
@Getter
@Setter
@TableName("edu_review")
public class EduReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private ReviewStatus status;

    private Long requestMemberId;

    private String requestMemberName;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private ReviewType refType;

    private Long refId;
    private Long refIdCourse;


    private String requestMsg;

    private String reviewMsg;

    private Long reviewMemberId;

    private String reviewMemberName;

    @Version
    private Long version;


}
