package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.entity.dto.ResearchDTO;
import com.venson.eduservice.entity.enums.LanguageEnum;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
public interface EduResearchService extends IService<EduResearch> {


//    List<EduResearch> getResearchReviewList();

    PageResponse<EduResearch> getResearchPage(Integer page, Integer limit);

    Long addResearch(ResearchDTO research);

    void updateResearch(Long id, ResearchDTO research);

    ResearchDTO getResearchPreviewById(Long id);

    PageResponse<EduResearch> getResearchReviewPage(Integer current, Integer size);

    void switchEnableById(Long id, LanguageEnum lang);

    void removeResearchById(Long id);
}
