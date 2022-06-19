package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-06-14
 */
@Getter
@Setter
@TableName("edu_chapter_section_content")
public class EduChapterSectionContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String chapterSectionId;

    /**
     * 0 for chapter, 1 for section
     */
    private Integer chapterSection;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @Version
    private Integer version;


}
