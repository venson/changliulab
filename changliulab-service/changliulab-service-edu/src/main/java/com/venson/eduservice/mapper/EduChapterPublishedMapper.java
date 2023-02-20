package com.venson.eduservice.mapper;

import com.venson.eduservice.entity.EduChapterPublished;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.venson.eduservice.entity.front.dto.ChapterFrontDTO;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
public interface EduChapterPublishedMapper extends BaseMapper<EduChapterPublished> {

    List<ChapterFrontDTO> getFrontChaptersByCourseId(Long id);
}
