package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.EduSection;
import com.venson.eduservice.entity.chapter.ChapterVo;
import com.venson.eduservice.entity.chapter.SectionVo;
import com.venson.eduservice.mapper.EduChapterMapper;
import com.venson.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.eduservice.service.EduSectionService;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@Service
@Slf4j
public class EduChapterServiceImp extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    private final EduSectionService eduSectionService;

    public EduChapterServiceImp(EduSectionService eduSectionService) {
        this.eduSectionService= eduSectionService;
    }

    @Override
    public List<ChapterVo> getChapterSectionByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId).orderByAsc("sort","id");
        List<EduChapter> chapterList = baseMapper.selectList(wrapperChapter);


        QueryWrapper<EduSection> wrapperSection = new QueryWrapper<>();
        wrapperSection.eq("course_id", courseId).orderBy(true,true,"chapter_id","sort");
        List<EduSection> sectionList = eduSectionService.list(wrapperSection);
        List<ChapterVo> finalList  = new ArrayList<>();
        Map<String, List<SectionVo>> sectionGroupMap = sectionList.parallelStream().
                collect(Collectors.groupingBy(EduSection::getChapterId,
                        Collectors.mapping(o -> new SectionVo(o.getId(), o.getTitle()),
                                Collectors.toList())));

        for (EduChapter chapter:
             chapterList) {
            ChapterVo chapterVoTemp = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVoTemp);
            List<SectionVo> sectionVos = sectionGroupMap.get(chapterVoTemp.getId());
            chapterVoTemp.setChildren(sectionVos);
            finalList.add(chapterVoTemp);
        }

        return finalList;
    }

    @Override
    public Boolean deleteChapter(String id) {
        QueryWrapper<EduSection> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        long count = eduSectionService.count(wrapper);
        if(count ==0){
            int i = baseMapper.deleteById(id);
            return i>0;
        }else{
            throw new CustomizedException(20001, "包含子章节，不能删除");
        }


    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);

    }
}
