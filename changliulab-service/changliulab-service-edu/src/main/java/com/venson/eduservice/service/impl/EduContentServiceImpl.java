package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.EduChapterDescription;
import com.venson.eduservice.entity.EduSection;
import com.venson.eduservice.entity.dto.CourseSyllabusDTO;
import com.venson.eduservice.service.EduChapterDescriptionService;
import com.venson.eduservice.service.EduChapterService;
import com.venson.eduservice.service.EduContentService;
import com.venson.eduservice.service.EduSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class EduContentServiceImpl implements EduContentService {
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduChapterDescriptionService chapterDescService;

    @Autowired
    private EduSectionService sectionService;
    @Override
    public List<CourseSyllabusDTO> getSyllabusByCourseId(Long courseId) {
        LambdaQueryWrapper<EduChapter> chapterWrapper = new LambdaQueryWrapper<>();
        chapterWrapper.eq(EduChapter::getCourseId, courseId).orderByAsc(EduChapter::getSort).orderByAsc(EduChapter::getId);
        List<EduChapter> chapters =chapterService.list(chapterWrapper);
        List<Long> chapterIds = chapters.stream().map(EduChapter::getId).toList();
        List<EduChapterDescription> chapterDescriptions =chapterDescService.listByIds(chapterIds);
        HashMap<Long, EduChapterDescription> descMap = new HashMap<>();
        chapterDescriptions.forEach(o->descMap.put(o.getId(),o));
        List<CourseSyllabusDTO> syllabus = chapters.stream().map(o -> new CourseSyllabusDTO(o.getId(), o.getTitle(), descMap.get(o.getId()).getDescription(),o.getReview(),o.getIsRemoveAfterReview())).toList();

        LambdaQueryWrapper<EduSection> sectionWrapper = new LambdaQueryWrapper<>();
        sectionWrapper.eq(EduSection::getCourseId, courseId).orderByAsc(EduSection::getChapterId).orderByAsc(EduSection::getSort).orderByAsc(EduSection::getId);
        List<EduSection> sections = sectionService.list(sectionWrapper);

        HashMap<Long, ArrayList<CourseSyllabusDTO>> chapterIdSectionMap = new LinkedHashMap<>();
        sections.forEach(o->{
            CourseSyllabusDTO section =  new CourseSyllabusDTO(o.getId(),o.getTitle(),null,o.getReview(), o.getIsRemoveAfterReview());
            Long chapterId = o.getChapterId();
            if(chapterIdSectionMap.containsKey(chapterId)){

                chapterIdSectionMap.get(chapterId).add(section);
            }else{
                ArrayList<CourseSyllabusDTO> sectionList = new ArrayList<>();
                sectionList.add(section);
                chapterIdSectionMap.put(chapterId,sectionList);
            }
        } );

        syllabus.forEach(o-> o.setChildren(chapterIdSectionMap.get(o.getId())));
        return syllabus;
    }
}
