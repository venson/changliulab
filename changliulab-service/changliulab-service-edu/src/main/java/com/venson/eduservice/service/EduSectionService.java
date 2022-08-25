package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduSection;
import com.venson.eduservice.entity.dto.SectionDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-06-13
 */
public interface EduSectionService extends IService<EduSection> {


    void updateSectionById(Long sectionId, SectionDTO sectionDTO);

    SectionDTO getSectionById(Long sectionId);

    Long addSection(SectionDTO section);

    void removeSectionById(Long sectionId);
}
