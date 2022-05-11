package com.venson.eduservice.service.impl;

import com.venson.eduservice.entity.EduCourse;
import com.venson.eduservice.entity.EduCourseDescription;
import com.venson.eduservice.entity.vo.CourseInfoVo;
import com.venson.eduservice.mapper.EduCourseMapper;
import com.venson.eduservice.service.EduCourseDescriptionService;
import com.venson.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@Service
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
}
