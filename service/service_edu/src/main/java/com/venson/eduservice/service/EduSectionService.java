package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.EduSection;
import com.venson.eduservice.entity.vo.SectionVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-06-13
 */
public interface EduSectionService extends IService<EduSection> {


    void updateSectionById(Long sectionId, SectionVo sectionVo);

    SectionVo getSectionById(Long sectionId);

    Long addSection(SectionVo section);

    void removeSectionById(Long sectionId);
}
