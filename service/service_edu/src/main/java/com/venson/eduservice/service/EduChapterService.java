package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.chapter.CourseTreeNodeVo;
import com.venson.eduservice.entity.vo.ChapterVo;

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


    void removeChapterById(Long chapterId);

    void updateChapterById(Long chapterId, ChapterVo chapter);
}
