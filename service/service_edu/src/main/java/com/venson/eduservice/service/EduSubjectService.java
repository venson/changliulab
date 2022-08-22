package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.subject.LevelISubject;
import com.venson.eduservice.entity.subject.SubjectTreeNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    List<SubjectTreeNode> getAllSubject();

}
