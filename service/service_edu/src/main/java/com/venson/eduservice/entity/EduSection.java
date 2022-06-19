package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2022-06-13
 */
@Getter
@Setter
@TableName("edu_section")
public class EduSection implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String courseId;

    private String chapterId;

    private String title;

    private String videoLink;

    private Integer available;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private String content;

    @Version
    private Long version;

    private Long viewCount;

    @TableLogic
    private Integer isDeleted;


}
