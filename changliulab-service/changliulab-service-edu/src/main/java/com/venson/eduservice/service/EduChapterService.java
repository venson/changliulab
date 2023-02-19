package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.dto.ChapterContentDTO;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.vo.CourseTreeNodeVo;
import com.venson.eduservice.entity.front.dto.ChapterFrontDTO;

import java.util.List;
import java.util.Map;

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


    void removeMarkChapterById(Long chapterId);

    void updateChapterById(Long chapterId, ChapterFrontDTO chapter);

    ChapterContentDTO getChapterDTOById(Long chapterId);

    Long addChapter(ChapterFrontDTO chapterFrontDTO);


    Map<Long, ReviewStatus> getChapterReviewStatusMap(boolean review);
}
