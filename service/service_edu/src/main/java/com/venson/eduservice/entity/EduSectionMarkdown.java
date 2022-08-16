package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("edu_section_markdown")
public class EduSectionMarkdown implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private Long id;

    private String markdown;


}
