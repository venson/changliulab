package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.chapter.CourseTreeNodeVo;
import com.venson.eduservice.entity.dto.ChapterDTO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
public interface EduChapterService extends IService<EduChapter> {

    List<CourseTreeNodeVo> getCourseTreeByCourseId(Long courseId);


    void deleteChapterSectionByCourseId(Long courseId);


    RMessage removeChapterById(Long chapterId);

    void updateChapterById(Long chapterId, ChapterDTO chapter);

    RMessage getChapterDTOById(Long chapterId);

    RMessage addChapter(ChapterDTO chapterDTO);
}
