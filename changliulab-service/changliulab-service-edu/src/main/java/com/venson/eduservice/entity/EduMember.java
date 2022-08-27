package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.venson.eduservice.entity.enums.MemberLevel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author baomidou
 * @since 2022-05-02
 */
@TableName("edu_member")
@Data
public class EduMember implements Serializable {

    private static final long serialVersionUID = 123492835L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String intro;

    private String career;

    private MemberLevel level;

    private String avatar;

    private String title;

    private Integer sort;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}