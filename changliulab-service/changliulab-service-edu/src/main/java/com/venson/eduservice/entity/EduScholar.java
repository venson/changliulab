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
 * @since 2022-06-18
 */
@Getter
@Setter
@TableName("edu_scholar")
public class EduScholar implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String year;

    private String authors;

    private String publicationDate;

    private String journal;

    private Integer volume;

    private Integer issue;

    private String pages;

    private String publisher;

    private String description;

    private String linkText;

    private String linkUrl;

    private Integer totalCitations;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @Version
    private Long version;

    @TableLogic
    private Integer isDeleted;


}
