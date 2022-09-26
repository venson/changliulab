package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.commonutils.Result;
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


    Result removeChapterById(Long chapterId);

    void updateChapterById(Long chapterId, ChapterDTO chapter);

    ChapterDTO getChapterDTOById(Long chapterId);

    Result addChapter(ChapterDTO chapterDTO);

    Long addEmptyChapter(Long courseId);
}
