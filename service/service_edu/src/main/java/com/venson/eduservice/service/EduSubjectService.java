package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.subject.TopSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-10
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile multipartFile, EduSubjectService eduSubjectService);

    List<TopSubject> getAllSubject();
}
