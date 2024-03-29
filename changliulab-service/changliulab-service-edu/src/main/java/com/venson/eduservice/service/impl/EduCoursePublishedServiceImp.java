package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduChapterPublished;
import com.venson.eduservice.entity.EduChapterPublishedDesc;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.EduSectionPublished;
import com.venson.eduservice.entity.front.dto.CourseFrontBriefDTO;
import com.venson.eduservice.entity.front.dto.CourseSyllabusFrontDTO;
import com.venson.eduservice.entity.front.vo.CourseFrontFilterVo;
import com.venson.eduservice.mapper.EduCoursePublishedMapper;
import com.venson.eduservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
@Service
public class EduCoursePublishedServiceImp extends ServiceImpl<EduCoursePublishedMapper, EduCoursePublished> implements EduCoursePublishedService {
    @Autowired
    private EduChapterPublishedService chapterPublishedService;
    @Autowired
    private EduSectionPublishedService sectionPublishedService;

    @Autowired
    private EduChapterPublishedDescService chapterPublishedDescService;



    @Override
    public PageResponse<EduCoursePublished> getFrontPageCourseList(Integer page, Integer limit, CourseFrontFilterVo courseFrontVo) {
        Page<EduCoursePublished> pageCourse = new Page<>(page,limit);
        LambdaQueryWrapper<EduCoursePublished> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(EduCoursePublished::getId,EduCoursePublished::getTitle,
                EduCoursePublished::getCover,EduCoursePublished::getIsPublic,
                EduCoursePublished::getViewCount,EduCoursePublished::getViewCount);
        if(courseFrontVo!=null){
            wrapper.eq(!ObjectUtils.isEmpty(courseFrontVo.getSubjectParentId()),
                    EduCoursePublished::getSubjectParentId, courseFrontVo.getSubjectParentId());
            wrapper.eq(!ObjectUtils.isEmpty(courseFrontVo.getSubjectId()),
                    EduCoursePublished::getSubjectId, courseFrontVo.getSubjectId());
            // order by views
            if(courseFrontVo.getSort()!=null){
                switch (courseFrontVo.getSort()){
                    case VIEWS-> wrapper.orderByDesc(true,EduCoursePublished::getViewCount);
                    case CREATE -> wrapper.orderByDesc(true, EduCoursePublished::getId);
                    case UPDATE -> wrapper.orderByDesc(true,EduCoursePublished::getGmtModified);
                }
            }
        }
        baseMapper.selectPage(pageCourse,wrapper);

        return PageUtil.toBean(pageCourse);
    }



    @Override
    public List<CourseFrontBriefDTO> getFrontIndexCourse() {
        return baseMapper.getFrontIndexCourse();
    }

    @Override
    public List<CourseSyllabusFrontDTO> getSyllabusByCourseId(Long id) {

        LambdaQueryWrapper<EduChapterPublished> chapterWrapper = new LambdaQueryWrapper<>();
        chapterWrapper.eq(EduChapterPublished::getCourseId, id).orderByAsc(EduChapterPublished::getSort).orderByAsc(EduChapterPublished::getId);
        List<EduChapterPublished> chapters =chapterPublishedService.list(chapterWrapper);
        List<Long> chapterIds = chapters.stream().map(EduChapterPublished::getId).toList();
        List<EduChapterPublishedDesc> chapterDescriptions =chapterPublishedDescService.listByIds(chapterIds);
        HashMap<Long, EduChapterPublishedDesc> descMap = new HashMap<>();
        chapterDescriptions.forEach(o->descMap.put(o.getId(),o));
        List<CourseSyllabusFrontDTO> syllabus = chapters.stream()
                .map(o -> new CourseSyllabusFrontDTO(o.getId(), o.getTitle(), descMap.get(o.getId()).getDescription())).toList();

        LambdaQueryWrapper<EduSectionPublished> sectionWrapper = new LambdaQueryWrapper<>();
        sectionWrapper.eq(EduSectionPublished::getCourseId, id).orderByAsc(EduSectionPublished::getChapterId).orderByAsc(EduSectionPublished::getSort).orderByAsc(EduSectionPublished::getId);
        List<EduSectionPublished> sections = sectionPublishedService.list(sectionWrapper);

        HashMap<Long, ArrayList<CourseSyllabusFrontDTO>> chapterIdSectionMap = new LinkedHashMap<>();
        sections.forEach(o->{
            CourseSyllabusFrontDTO section =  new CourseSyllabusFrontDTO(o.getId(),o.getTitle(),null);
            Long chapterId = o.getChapterId();
            if(chapterIdSectionMap.containsKey(chapterId)){

                chapterIdSectionMap.get(chapterId).add(section);
            }else{
                ArrayList<CourseSyllabusFrontDTO> sectionList = new ArrayList<>();
                sectionList.add(section);
                chapterIdSectionMap.put(chapterId,sectionList);
            }
        } );

        syllabus.forEach(o-> o.setChildren(chapterIdSectionMap.get(o.getId())));
        return syllabus;
    }


}
