package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    private final EduSectionMarkdownService markdownService;

    public EduSectionServiceImp(EduSectionMarkdownService markdownService) {
        this.markdownService = markdownService;
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

    @Override
    public Long addEmptySection(Long courseId, Long chapterId) {
        // auto set sort for section ,get the count of sections under the courseId and chapterId
        LambdaQueryWrapper<EduSection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSection::getCourseId,courseId).eq(EduSection::getChapterId, chapterId);
        Long selectCount = baseMapper.selectCount(wrapper);
        // add new Empty Section.
        EduSection eduSection = new EduSection();
        eduSection.setTitle("New Section");
        eduSection.setCourseId(courseId);
        eduSection.setChapterId(chapterId);
        eduSection.setSort(Math.toIntExact(++selectCount));
        baseMapper.insert(eduSection);
        // add section markdown with the same id
        EduSectionMarkdown sectionMarkdown = new EduSectionMarkdown();
        sectionMarkdown.setId(eduSection.getId());
        sectionMarkdown.setMarkdown("Please Edit");
        markdownService.save(sectionMarkdown);
        return eduSection.getId();
    }
}
