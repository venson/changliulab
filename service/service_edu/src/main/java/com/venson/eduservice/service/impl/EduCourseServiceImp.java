package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.EduCourseDescription;
import com.venson.eduservice.entity.vo.CourseInfoVo;
import com.venson.eduservice.mapper.EduCourseMapper;
import com.venson.eduservice.service.EduCourseDescriptionService;
import com.venson.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
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
        BeanUtils.copyProperties(eduCourseDescriptionService,infoVo);
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
        boolean descRow = eduCourseDescriptionService.updateById(description);
        if(descRow){
            throw new CustomizedException(20001,"修改课程描述失败");
        }
    }


}
