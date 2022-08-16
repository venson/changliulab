package com.venson.eduservice.service.impl;

import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.vo.SectionVo;
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
    public void updateSectionById(Long sectionId, SectionVo sectionVo) {
        EduSection eduSection = new EduSection();
        EduSectionMarkdown eduSectionMarkdown = new EduSectionMarkdown();
        BeanUtils.copyProperties(sectionVo,eduSection);
        eduSection.setIsModified(true);
        eduSectionMarkdown.setMarkdown(sectionVo.getMarkdown());
        eduSectionMarkdown.setId(sectionId);
        baseMapper.updateById(eduSection);
        markdownService.updateById(eduSectionMarkdown);
    }

    @Override
    public SectionVo getSectionById(Long sectionId) {
        EduSection section = baseMapper.selectById(sectionId);
        EduSectionMarkdown sectionMd = markdownService.getById(sectionId);
        SectionVo sectionVo = new SectionVo();
        BeanUtils.copyProperties(section,sectionVo);
        sectionVo.setMarkdown(sectionMd.getMarkdown());
        return sectionVo;
    }

    @Override
    public Long addSection(SectionVo section) {
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
