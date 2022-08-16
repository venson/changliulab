package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.EduCourseDescription;
import com.venson.eduservice.entity.vo.CourseInfoVo;
import com.venson.eduservice.entity.vo.CoursePreviewVo;
import com.venson.eduservice.mapper.EduCourseMapper;
import com.venson.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // base service
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    // published service
    @Autowired
    private EduChapterPublishedService chapterPublishedService;
    @Autowired
    private EduSectionPublishedService sectionPublishedService;
    @Autowired
    private EduCourseDescriptionPublishedService descriptionPublishedService;
    @Autowired
    private EduCourseDescriptionService descriptionService;


    @Override
    public Long addCourse(CourseInfoVo courseInfoVo) {
        EduCourse newCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,newCourse);
        int insert = baseMapper.insert(newCourse);
        if (insert ==0){
            throw new CustomizedException(20001,"添加课程失败");
        }
        Long id = newCourse.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(id);
        eduCourseDescriptionService.save(courseDescription);
        return id;
    }

    @Override
    public CourseInfoVo getCourseById(Long id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        CourseInfoVo infoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,infoVo);
        BeanUtils.copyProperties(courseDescription,infoVo);
        return infoVo;
    }

    @Override
    @Transactional(rollbackFor = {CustomizedException.class})
    public void updateCourse(CourseInfoVo infoVo) {
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



    @Transactional
    @Override
    public void removeCourseById(Long courseId) {

        // mark isRemoveAfterReview, deletion will perform after review.
        chapterService.deleteChapterSectionByCourseId(courseId);

        EduCourse eduCourse = baseMapper.selectById(courseId);
        eduCourse.setIsRemoveAfterReview(true);
        baseMapper.updateById(eduCourse);
    }



    @Override
    public Map<String,Object> getPageCoursePublishVo(Integer pageNum, Integer limit, String condition) {

        Page<CoursePreviewVo> page = new Page<>(pageNum, limit);
        QueryWrapper<CoursePreviewVo> wrapper = new QueryWrapper<>();
        if (condition != null && !condition.isEmpty()){
            wrapper.like("c.title",condition);
        }
         baseMapper.selectPageCoursePublishVo(page,wrapper);

        return PageUtil.toMap(page);

    }

    @Override
    public CoursePreviewVo getCoursePreviewById(Long courseId) {
        return baseMapper.getCoursePreviewById(courseId);
    }

    @Override
    public Map<String, Object> getPageReviewCoursePreviewVo(Integer pageNum, Integer limit, QueryWrapper<CoursePreviewVo> courseWrapper) {
        Page<CoursePreviewVo> page = new Page<>(pageNum, limit);
        baseMapper.selectPageCoursePublishVo(page,courseWrapper);
        return PageUtil.toMap(page);
    }

    @Override
    public void actualRemoveCourseById(Long courseId) {
        baseMapper.deleteById(courseId);
        eduCourseDescriptionService.removeById(courseId);
    }


}
