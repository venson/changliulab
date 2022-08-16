package com.venson.eduservice.entity.vo;

import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.EduChapterMarkdown;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ChapterVo extends EduChapter {
    private EduChapterMarkdown markdown;
}
