package com.venson.eduservice.service.impl;

import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.dto.SectionDTO;
import com.venson.eduservice.mapper.EduSectionMapper;
import com.venson.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-06-13
 */
@Service
public class EduSectionServiceImp extends ServiceImpl<EduSectionMapper, EduSection> implements EduSectionService {

    private final EduSectionPublishedService sectionPublishedService;
    private final EduSectionMarkdownService markdownService;
    private final EduSectionPublishedMdService publishedMdService;

    public EduSectionServiceImp(EduSectionPublishedService sectionPublishedService, EduSectionMarkdownService markdownService, EduSectionPublishedMdService publishedMdService) {
        this.sectionPublishedService = sectionPublishedService;
        this.markdownService = markdownService;
        this.publishedMdService = publishedMdService;
    }

    @Override
    public void updateSectionById(Long sectionId, SectionDTO sectionDTO) {
        EduSection eduSection = new EduSection();
        EduSectionMarkdown eduSectionMarkdown = new EduSectionMarkdown();
        BeanUtils.copyProperties(sectionDTO,eduSection);
        eduSection.setIsModified(true);
        eduSectionMarkdown.setMarkdown(sectionDTO.getMarkdown());
        eduSectionMarkdown.setId(sectionId);
        baseMapper.updateById(eduSection);
        markdownService.updateById(eduSectionMarkdown);
    }

    @Override
    public SectionDTO getSectionById(Long sectionId) {
        EduSection section = baseMapper.selectById(sectionId);
        EduSectionMarkdown sectionMd = markdownService.getById(sectionId);
        SectionDTO sectionDTO = new SectionDTO();
        BeanUtils.copyProperties(section, sectionDTO);
        sectionDTO.setMarkdown(sectionMd.getMarkdown());
        return sectionDTO;
    }

    @Override
    public Long addSection(SectionDTO section) {
        EduSection eduSection = new EduSection();
        BeanUtils.copyProperties(section,eduSection);
        EduSectionMarkdown sectionMarkdown = new EduSectionMarkdown();
        baseMapper.insert(eduSection);
        // use the same id to save section markdown.
        sectionMarkdown.setId(eduSection.getId());
        sectionMarkdown.setMarkdown(section.getMarkdown());
        markdownService.save(sectionMarkdown);
        return section.getId();
    }

    @Override
    public void removeSectionById(Long sectionId) {
        EduSection eduSection = baseMapper.selectById(sectionId);
        eduSection.setIsRemoveAfterReview(true);
        baseMapper.updateById(eduSection);
    }
}
