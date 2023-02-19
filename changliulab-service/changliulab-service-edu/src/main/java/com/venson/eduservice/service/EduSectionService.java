package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduSection;
import com.venson.eduservice.entity.dto.SectionContentDTO;
import com.venson.eduservice.entity.dto.SectionPreviewDTO;
import com.venson.eduservice.entity.enums.ReviewStatus;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-06-13
 */
public interface EduSectionService extends IService<EduSection> {


    void updateSectionById(Long sectionId, SectionContentDTO sectionContentDTO);

    SectionContentDTO getSectionById(Long sectionId);

    Long addSection(SectionContentDTO section);

    void removeMarkSectionById(Long sectionId);

//    Long addEmptySection(Long courseId, Long chapterId);

    SectionPreviewDTO getSectionPreviewBySectionId(Long id);

//    Set<Long> getAppliedSectionCourseIdSet();

    Map<Long, ReviewStatus> getSectionReviewStatusMap(boolean review);
}
