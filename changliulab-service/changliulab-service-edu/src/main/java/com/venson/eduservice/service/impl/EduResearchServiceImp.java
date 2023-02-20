package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.entity.dto.ResearchDTO;
import com.venson.eduservice.entity.enums.LanguageEnum;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.mapper.EduResearchMapper;
import com.venson.eduservice.service.EduResearchService;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@Service
@Slf4j
public class EduResearchServiceImp extends ServiceImpl<EduResearchMapper, EduResearch> implements EduResearchService {

//    @Override
//    public List<EduResearch> getResearchReviewList() {
//        LambdaQueryWrapper<EduResearch> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(EduResearch::getReview, ReviewStatus.APPLIED);
//        return baseMapper.selectList(wrapper);
//    }

    @Override
    public PageResponse<EduResearch> getResearchPage(Integer page, Integer limit) {
        Page<EduResearch> researchPage = new Page<>(page, limit);
        LambdaQueryWrapper<EduResearch> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduResearch::getId, EduResearch::getTitle, EduResearch::getReview, EduResearch::getIsModified
                , EduResearch::getLanguage, EduResearch::getEnable);
        baseMapper.selectPage(researchPage, wrapper);
        return PageUtil.toBean(researchPage);
    }

    @Override
    public Long addResearch(ResearchDTO research) {
        if (isDuplicatedTitle(research.getTitle(), null)) {
            throw new CustomizedException(200001, "Duplicated research Title");
        }
        EduResearch eduResearch = new EduResearch();
        eduResearch.setLanguage(research.getLanguage());
        eduResearch.setMarkdown(research.getMarkdown());
        eduResearch.setHtmlBrBase64(research.getHtmlBrBase64());
        eduResearch.setTitle(research.getTitle());
        baseMapper.insert(eduResearch);
        return eduResearch.getId();
    }

    private boolean isDuplicatedTitle(String title, Long excludeId) {
        LambdaQueryWrapper<EduResearch> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduResearch::getTitle, title);
        if(!ObjectUtils.isEmpty(excludeId)){
                wrapper.ne(EduResearch::getId, excludeId);
        }
        EduResearch duplicatedTitle = baseMapper.selectOne(wrapper);
        return duplicatedTitle != null;
    }

    @Override
    public void updateResearch(Long id, ResearchDTO research) {
        EduResearch eduResearch = baseMapper.selectById(id);
        Assert.notNull(eduResearch, "No corresponding research");
        if (eduResearch.getReview() == ReviewStatus.APPLIED) {
            throw new CustomizedException(200001, "Research is under review");
        }
        if (!research.getTitle().equals(eduResearch.getTitle())  && isDuplicatedTitle(research.getTitle(), research.getId())) {
            throw new CustomizedException(200001, "Duplicated research title");
        }
        eduResearch.setTitle(research.getTitle());
        if(!research.getHtmlBrBase64().equals(eduResearch.getHtmlBrBase64())){
            eduResearch.setIsModified(true);
            eduResearch.setMarkdown(research.getMarkdown());
            eduResearch.setHtmlBrBase64(research.getHtmlBrBase64());
        }else{
            eduResearch.setMarkdown(null);
            eduResearch.setHtmlBrBase64(null);
        }
        baseMapper.updateById(eduResearch);

    }

    @Override
    public ResearchDTO getResearchPreviewById(Long id) {
        EduResearch eduResearch = baseMapper.selectById(id);
        ResearchDTO view = new ResearchDTO();
        view.setHtmlBrBase64(eduResearch.getHtmlBrBase64());
        view.setPublishedHtmlBrBase64(eduResearch.getPublishedHtmlBrBase64());
        view.setTitle(eduResearch.getTitle());
        view.setLanguage(eduResearch.getLanguage());
        view.setReview(eduResearch.getReview());
        view.setEnable(eduResearch.getEnable());
        view.setIsModified(eduResearch.getIsModified());
        return view;

    }

    @Override
    public PageResponse<EduResearch> getResearchReviewPage(Integer current, Integer size) {
        LambdaQueryWrapper<EduResearch> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduResearch::getReview, ReviewStatus.APPLIED)
                .select(EduResearch::getId, EduResearch::getTitle, EduResearch::getReview, EduResearch::getIsModified
                , EduResearch::getLanguage, EduResearch::getEnable);
        Page<EduResearch> page = new Page<>(current, size);
        baseMapper.selectPage(page,wrapper);

        return PageUtil.toBean(page);
    }

    @Override
    @Transactional
    public void switchEnableById(Long id, LanguageEnum lang) {
        EduResearch eduResearch = baseMapper.selectById(id);
        if(!eduResearch.getEnable()){
            LambdaUpdateWrapper<EduResearch> wrapper = Wrappers.lambdaUpdate();
            wrapper.ne(EduResearch::getId, id).eq(EduResearch::getIsPublished,true)
                    .eq(EduResearch::getLanguage, lang)
                    .set(EduResearch::getEnable, false);
            baseMapper.update(null,wrapper);
        }
        eduResearch.setEnable(!eduResearch.getEnable());
        baseMapper.updateById(eduResearch);
    }

    @Override
    @Transactional
    public void removeResearchById(Long id) {
        EduResearch research = baseMapper.selectById(id);
        research.setIsRemoveAfterReview(false);
        baseMapper.updateById(research);
    }
}
