package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.chapter.ChapterVo;

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

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    Boolean deleteChapter(String id);

    void removeChapterByCourseId(String courseId);
}
