package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.eduservice.entity.EduChapterSectionContent;
import com.venson.eduservice.mapper.EduChapterSectionContentMapper;
import com.venson.eduservice.service.EduChapterSectionContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-06-14
 */
@Service
public class EduChapterSectionContentServiceImp extends ServiceImpl<EduChapterSectionContentMapper, EduChapterSectionContent> implements EduChapterSectionContentService {

    @Override
    public EduChapterSectionContent getChapterById(String id) {
        QueryWrapper<EduChapterSectionContent> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_section", 0);
        wrapper.eq("chapter_section_id",id);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public EduChapterSectionContent getSectionById(String id) {
        QueryWrapper<EduChapterSectionContent> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_section", 1);
        wrapper.eq("chapter_section_id",id);
        return baseMapper.selectOne(wrapper);
    }
}
