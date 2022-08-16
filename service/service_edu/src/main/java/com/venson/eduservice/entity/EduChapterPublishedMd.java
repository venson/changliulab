package com.venson.eduservice.entity;

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
@TableName("edu_chapter_published_md")
public class EduChapterPublishedMd implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String markdown;


}
