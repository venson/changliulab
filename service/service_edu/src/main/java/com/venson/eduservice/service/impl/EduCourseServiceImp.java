package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.EduCourseDescription;
import com.venson.eduservice.entity.frontvo.CourseFrontVo;
import com.venson.eduservice.entity.vo.CourseInfoVo;
import com.venson.eduservice.entity.vo.CoursePublishVo;
import com.venson.eduservice.mapper.EduCourseMapper;
import com.venson.eduservice.service.EduChapterService;
import com.venson.eduservice.service.EduCourseDescriptionService;
import com.venson.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.eduservice.service.EduVideoService;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class EduCourseServiceImp extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    private final EduCourseDescriptionService eduCourseDescriptionService;

    private final EduVideoService eduVideoService;

    private final EduChapterService eduChapterService;

    public EduCourseServiceImp(EduCourseDescriptionService eduCourseDescriptionService, EduVideoService eduVideoService, EduChapterService eduChapterService) {
        this.eduCourseDescriptionService = eduCourseDescriptionService;
        this.eduVideoService = eduVideoService;
        this.eduChapterService = eduChapterService;
    }

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse newCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,newCourse);
        int insert = baseMapper.insert(newCourse);
        if (insert ==0){
            throw new CustomizedException(20001,"添加课程失败");
        }

        String id = newCourse.getId();


        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(id);
        eduCourseDescriptionService.save(courseDescription);
        return id;
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        CourseInfoVo infoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,infoVo);
        BeanUtils.copyProperties(courseDescription,infoVo);
        return infoVo;
    }

    @Override
    @Transactional(rollbackFor = {CustomizedException.class})
    public void updateCourseInfo(CourseInfoVo infoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(infoVo,eduCourse);
        int courseRows = baseMapper.updateById(eduCourse);
        if(courseRows == 0){
            throw new CustomizedException(20001,"修改课程失败");
        }

        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(infoVo,description);
        eduCourseDescriptionService.updateById(description);
    }


    @Override
    public IPage<CoursePublishVo> selectPageVo(IPage<CoursePublishVo> page, Wrapper<CoursePublishVo> wrapper) {
        List<CoursePublishVo> publishCourseInfo = baseMapper.getPublishCourseInfo(wrapper);
        page.setRecords(publishCourseInfo);
        return page;
    }

    @Override
    public CoursePublishVo getPublishCourseInfoById(String id) {
        return baseMapper.getPublishCourseInfoById(id);
    }

    @Override
    public void removeCourseById(String courseId) {
        eduVideoService.removeVideoByCourseId(courseId);

        eduChapterService.removeChapterByCourseId(courseId);

        eduCourseDescriptionService.removeById(courseId);

        int i = baseMapper.deleteById(courseId);
        if(i ==0){
            throw new CustomizedException(20001, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getFrontPageCourseList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if(!ObjectUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        Integer viewSort = courseFrontVo.getViewSort();
        if(courseFrontVo.getViewSort()!=0){
            if(viewSort ==1) {
                wrapper.orderByDesc("view_count");
            }else{
                wrapper.orderByAsc("view_count");
            }
        }
        Integer createSort = courseFrontVo.getGmtCreateSort();
        if(createSort!= 0){
            if(createSort ==1) {
                wrapper.orderByDesc("gmt_create");
            }else{
                wrapper.orderByAsc("gmt_create");
            }
        }
        Integer updateSort= courseFrontVo.getUpdateSort();
        if(updateSort != 0){
            if(updateSort ==1) {
                wrapper.orderByDesc("gmt_modified");
            }else{
                wrapper.orderByAsc("gmt_modified");
            }
        }
        baseMapper.selectPage(pageCourse,wrapper);
        List<EduCourse> records = pageCourse.getRecords();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        long pages = pageCourse.getPages();
        boolean hasPrevious = pageCourse.hasPrevious();
        boolean hasNext = pageCourse.hasNext();
        long current = pageCourse.getCurrent();

        HashMap<String, Object> map = new HashMap<>();
        map.put("records",records);
        map.put("size", size);
        map.put("total",total);
        map.put("pages", pages);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);
        map.put("current", current);

        return map;
    }


}
