package com.venson.eduservice.service.front;

import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.front.dto.ChapterFrontDTO;
import com.venson.eduservice.entity.front.dto.CourseFrontBriefDTO;
import com.venson.eduservice.entity.front.dto.SectionFrontDTO;
import com.venson.eduservice.entity.front.vo.CourseFrontInfoDTO;
import com.venson.eduservice.entity.front.vo.CourseFrontTreeNodeVo;

import java.util.List;

public interface CourseFrontService {
    CourseFrontInfoDTO getFrontCourseInfo(Long id);

    PageResponse<EduCoursePublished> getPageCourseByMemberId(Long id, Integer page, Integer limit);

    SectionFrontDTO getSectionBySectionId(Long id);

    ChapterFrontDTO getChapterByChapterId(Long id);

    List<CourseFrontBriefDTO> getFrontIndexCourse();


    List<CourseFrontTreeNodeVo> getCourseFrontTreeByCourseId(Long id);
}
