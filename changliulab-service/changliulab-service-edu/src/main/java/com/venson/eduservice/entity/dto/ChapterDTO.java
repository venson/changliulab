package com.venson.eduservice.entity.dto;

import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.EduChapterMarkdown;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ChapterDTO extends EduChapter {
    private String markdown;
}
