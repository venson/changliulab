package com.venson.eduservice.service.impl;

import com.venson.eduservice.entity.EduChapterMarkdown;
import com.venson.eduservice.entity.EduChapterPublishedMd;
import com.venson.eduservice.mapper.EduChapterMarkdownMapper;
import com.venson.eduservice.service.EduChapterMarkdownService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.eduservice.service.EduChapterPublishedMdService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
@Service
public class EduChapterMarkdownServiceImp extends ServiceImpl<EduChapterMarkdownMapper, EduChapterMarkdown> implements EduChapterMarkdownService {
    private final EduChapterPublishedMdService publishedMdService;

    public EduChapterMarkdownServiceImp(EduChapterPublishedMdService publishedMdService) {
        this.publishedMdService = publishedMdService;
    }

    @Override
    public void saveOrUpdateToPublishedMd(List<Long> updateIdList) {
        List<EduChapterMarkdown> markdownList = baseMapper.selectBatchIds(updateIdList);
        EduChapterPublishedMd publishedMd = new EduChapterPublishedMd();
        markdownList.parallelStream().forEach(o->{
            BeanUtils.copyProperties(o,publishedMd);
            publishedMdService.saveOrUpdate(publishedMd);
        });
    }
}
