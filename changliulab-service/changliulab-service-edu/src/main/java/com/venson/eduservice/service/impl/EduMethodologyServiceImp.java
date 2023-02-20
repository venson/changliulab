package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.entity.dto.MethodologyDTO;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.mapper.EduMethodologyMapper;
import com.venson.eduservice.service.EduMethodologyService;
import com.venson.servicebase.exception.CustomizedException;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@Service
public class EduMethodologyServiceImp extends ServiceImpl<EduMethodologyMapper, EduMethodology> implements EduMethodologyService {

//    @Override
//    public List<EduMethodology> getMethodologyReviewList() {
//        LambdaQueryWrapper<EduMethodology> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(EduMethodology::getReview, ReviewStatus.APPLIED);
//        return baseMapper.selectList(wrapper);
//    }

    @Override
    public PageResponse<EduMethodology> getMethodologyPage(Integer page, Integer limit) {
        Page<EduMethodology> methodologyPage = new Page<>(page, limit);
        LambdaQueryWrapper<EduMethodology> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduMethodology::getId, EduMethodology::getTitle, EduMethodology::getReview, EduMethodology::getIsModified
                , EduMethodology::getLanguage, EduMethodology::getIsPublished);
        baseMapper.selectPage(methodologyPage, wrapper);
        return PageUtil.toBean(methodologyPage);
    }

    @Override
    public MethodologyDTO getMethodologyViewById(Long id) {

        EduMethodology eduMethodology = baseMapper.selectById(id);
        MethodologyDTO view = new MethodologyDTO();
        view.setHtmlBrBase64(eduMethodology.getHtmlBrBase64());
        view.setPublishedHtmlBrBase64(eduMethodology.getPublishedHtmlBrBase64());
        view.setTitle(eduMethodology.getTitle());
        view.setLanguage(eduMethodology.getLanguage());
        view.setReview(eduMethodology.getReview());
        view.setIsPublished(eduMethodology.getIsPublished());
        view.setIsModified(eduMethodology.getIsModified());
        return view;
    }

    @Override
    public void addMethodology(MethodologyDTO methodology) {

        if (isDuplicatedTitle(methodology.getTitle())) {
            throw new CustomizedException(200001, "Duplicated methodology Title");
        }

        EduMethodology eduMethodology = new EduMethodology();
        eduMethodology.setLanguage(methodology.getLanguage());
        eduMethodology.setMarkdown(methodology.getMarkdown());
        eduMethodology.setHtmlBrBase64(methodology.getHtmlBrBase64());
        eduMethodology.setTitle(methodology.getTitle());
        baseMapper.insert(eduMethodology);
    }

    @Override
    public void updateMethodology(Long id, MethodologyDTO methodology) {

        EduMethodology eduMethodology = baseMapper.selectById(id);
        Assert.notNull(eduMethodology, "No corresponding methodology");
        if (eduMethodology.getReview() == ReviewStatus.APPLIED) {
            throw new CustomizedException(200001, "Methodology is under review");
        }
        if (methodology.getTitle()!= null && !methodology.getTitle().equals(eduMethodology.getTitle())  && isDuplicatedTitle(methodology.getTitle())) {
            throw new CustomizedException(200001, "Duplicated methodology title");
        }
        eduMethodology.setTitle(methodology.getTitle());
        if(!methodology.getHtmlBrBase64().equals(eduMethodology.getHtmlBrBase64())){
            eduMethodology.setIsModified(true);
            eduMethodology.setMarkdown(methodology.getMarkdown());
            eduMethodology.setHtmlBrBase64(methodology.getHtmlBrBase64());
        }else{
            eduMethodology.setMarkdown(null);
            eduMethodology.setHtmlBrBase64(null);
        }
        baseMapper.updateById(eduMethodology);
    }

    @Override
    public PageResponse<EduMethodology> getMethodologyReviewPage(Integer current, Integer size) {

        LambdaQueryWrapper<EduMethodology> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduMethodology::getReview, ReviewStatus.APPLIED)
                .select(EduMethodology::getId, EduMethodology::getTitle, EduMethodology::getReview, EduMethodology::getIsModified
                        , EduMethodology::getLanguage, EduMethodology::getIsPublished);
        Page<EduMethodology> page = new Page<>(current, size);
        baseMapper.selectPage(page,wrapper);

        return PageUtil.toBean(page);
    }

    private boolean isDuplicatedTitle(String title) {
        LambdaQueryWrapper<EduMethodology> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduMethodology::getTitle, title);
        EduMethodology duplicatedTitle = baseMapper.selectOne(wrapper);
        return duplicatedTitle != null;
    }
}
