package com.venson.eduservice.service.front.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.front.dto.ChapterFrontDTO;
import com.venson.eduservice.entity.front.dto.CourseFrontBriefDTO;
import com.venson.eduservice.entity.front.dto.CourseSyllabusFrontDTO;
import com.venson.eduservice.entity.front.dto.SectionFrontDTO;
import com.venson.eduservice.entity.front.vo.CourseFrontInfoDTO;
import com.venson.eduservice.entity.front.vo.CourseFrontTreeNodeVo;
import com.venson.eduservice.service.*;
import com.venson.eduservice.service.front.CourseFrontService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseFrontServiceImpl implements CourseFrontService {
    @Autowired
    private EduCoursePublishedService coursePublishedService;

    @Autowired
    private EduSectionPublishedService sectionPublishedService;
    @Autowired
    private EduSectionPublishedMdService sectionPublishedMdService;

    @Autowired
    private EduChapterPublishedService chapterPublishedService;
    @Autowired
    private EduChapterPublishedDescService chapterPublishedDescService;



    @Autowired
    private EduMemberService memberService;

    @Autowired
    private EduSubjectService subjectService;

    @Override
    @Cacheable(value = "courseFrontInfo", key = "#id")
    public CourseFrontInfoDTO getFrontCourseInfo(Long id) {
        EduCoursePublished course = coursePublishedService.getById(id);
        EduSubject subject = subjectService.getById(course.getSubjectId());
        EduSubject parentSubject = subjectService.getById(course.getSubjectParentId());
        EduMember member = memberService.getById(course.getMemberId());
        CourseFrontInfoDTO infoDTO = new CourseFrontInfoDTO();
        BeanUtils.copyProperties(course, infoDTO);
        infoDTO.setL1Subject(parentSubject.getTitle());
        infoDTO.setL1SubjectId(parentSubject.getId());
        infoDTO.setL2Subject(subject.getTitle());
        infoDTO.setL2SubjectId(subject.getId());
        infoDTO.setMemberName(member.getName());
        infoDTO.setMemberTitle(member.getTitle());
        infoDTO.setAvatar(member.getAvatar());
        List<CourseSyllabusFrontDTO> syllabus = coursePublishedService.getSyllabusByCourseId(id);
        infoDTO.setSyllabus(syllabus);
        return infoDTO;
    }

    @Override
    @Cacheable(value = "member:course", key = "#id +':'+#page+':'+#limit")
    public PageResponse<EduCoursePublished> getPageCourseByMemberId(Long id, Integer page, Integer limit) {
        Page<EduCoursePublished> coursePage = new Page<>(page, limit);
        LambdaQueryWrapper<EduCoursePublished> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduCoursePublished::getMemberId, id);
        coursePublishedService.page(coursePage, wrapper);
        return PageUtil.toBean(coursePage);
    }

    @Override
    @Cacheable(value = "section", key = "'id:'+#id")
    public SectionFrontDTO getSectionBySectionId(Long id) {

        EduSectionPublished section = sectionPublishedService.getById(id);
        EduCoursePublished course = coursePublishedService.getById(section.getCourseId());
        EduSectionPublishedMd md = sectionPublishedMdService.getById(id);

        return new SectionFrontDTO(section.getTitle(), md.getHtmlBrBase64(), section.getVideoLink(), course.getIsPublic());
    }

    @Override
    public ChapterFrontDTO getChapterByChapterId(Long id) {
        EduChapterPublished chapter = chapterPublishedService.getById(id);
        EduChapterPublishedDesc desc = chapterPublishedDescService.getById(id);
        ChapterFrontDTO chapterDTO = new ChapterFrontDTO();
        BeanUtils.copyProperties(chapter, chapterDTO);
        chapterDTO.setDescription(desc.getDescription());
        return chapterDTO;
    }

    @Override
    @Cacheable(value = "index:course")
    public List<CourseFrontBriefDTO> getFrontIndexCourse() {
        return coursePublishedService.getFrontIndexCourse();
    }

    //    @Override
//    public List<CourseSyllabusFrontDTO> getSyllabusByCourseId(Long courseId) {
//
//        LambdaQueryWrapper<EduChapterPublished> chapterWrapper = new LambdaQueryWrapper<>();
//        chapterWrapper.eq(EduChapterPublished::getCourseId, courseId).orderByAsc(EduChapterPublished::getSort).orderByAsc(EduChapterPublished::getId);
//        List<EduChapterPublished> chapters =chapterPublishedService.list(chapterWrapper);
//        List<Long> chapterIds = chapters.stream().map(EduChapterPublished::getId).toList();
//        List<EduChapterPublishedDesc> chapterDescriptions =chapterPublishedDescService.listByIds(chapterIds);
//        HashMap<Long, EduChapterPublishedDesc> descMap = new HashMap<>();
//        chapterDescriptions.forEach(o->descMap.put(o.getId(),o));
//        List<CourseSyllabusFrontDTO> syllabus = chapters.stream()
//                .map(o -> new CourseSyllabusFrontDTO(o.getId(), o.getTitle(), descMap.get(o.getId()).getDescription())).toList();
//
//        LambdaQueryWrapper<EduSectionPublished> sectionWrapper = new LambdaQueryWrapper<>();
//        sectionWrapper.eq(EduSectionPublished::getCourseId, courseId).orderByAsc(EduSectionPublished::getChapterId).orderByAsc(EduSectionPublished::getSort).orderByAsc(EduSectionPublished::getId);
//        List<EduSectionPublished> sections = sectionPublishedService.list(sectionWrapper);
//
//        HashMap<Long, ArrayList<CourseSyllabusFrontDTO>> chapterIdSectionMap = new LinkedHashMap<>();
//        sections.forEach(o->{
//            CourseSyllabusFrontDTO section =  new CourseSyllabusFrontDTO(o.getId(),o.getTitle(),null);
//            Long chapterId = o.getChapterId();
//            if(chapterIdSectionMap.containsKey(chapterId)){
//
//                chapterIdSectionMap.get(chapterId).add(section);
//            }else{
//                ArrayList<CourseSyllabusFrontDTO> sectionList = new ArrayList<>();
//                sectionList.add(section);
//                chapterIdSectionMap.put(chapterId,sectionList);
//            }
//        } );
//
//        syllabus.forEach(o-> o.setChildren(chapterIdSectionMap.get(o.getId())));
//        return syllabus;
//    }
    @Override
    @Cacheable(value = "courseFrontTree", key = "#id")
    public List<CourseFrontTreeNodeVo> getCourseFrontTreeByCourseId(Long id) {
        List<ChapterFrontDTO> chapters = chapterPublishedService.getFrontChaptersByCourseId(id);

        LambdaQueryWrapper<EduSectionPublished> sectionWrapper = new LambdaQueryWrapper<>();
        sectionWrapper.eq(EduSectionPublished::getCourseId, id)
                .orderByAsc(Arrays.asList(EduSectionPublished::getSort, EduSectionPublished::getId));
        List<EduSectionPublished> sectionList = sectionPublishedService.list(sectionWrapper);

        List<CourseFrontTreeNodeVo> treeRootNode = chapters.stream().map(o ->
                new CourseFrontTreeNodeVo(o.getId(), o.getTitle(), o.getDescription())
        ).collect(Collectors.toList());

        Map<Long, List<CourseFrontTreeNodeVo>> treeLeafNode = sectionList.parallelStream()
                .collect(Collectors.groupingBy(EduSectionPublished::getChapterId,
                        Collectors.mapping(o -> new CourseFrontTreeNodeVo(o.getId(), o.getTitle()),
                                Collectors.toList())));

        treeRootNode.parallelStream().forEach(o -> o.setChildren(treeLeafNode.get(o.getId())));
        return treeRootNode;
    }
}
