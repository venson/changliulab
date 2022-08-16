package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
@TableName("edu_chapter_published")
public class EduChapterPublished implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 章节ID
     */
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
     * 显示排序
     */
    private Integer sort;

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
