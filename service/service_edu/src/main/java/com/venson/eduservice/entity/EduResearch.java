package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-07-04
 */
@Getter
@Setter
@TableName("edu_research")
public class EduResearch implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String markdown;

    private String publishedMd;

    private String language;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    private Boolean isModified;

    private Boolean publishRequest;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
