package com.venson.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.eduservice.entity.EduSubject;
import com.venson.eduservice.entity.excel.SubjectCategory;
import com.venson.eduservice.entity.subject.SubjectTreeNode;
import com.venson.eduservice.listener.SubjectExcelListener;
import com.venson.eduservice.mapper.EduSubjectMapper;
import com.venson.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-10
 */
@Service
@Slf4j
public class EduSubjectServiceImp extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile multipartFile, EduSubjectService eduSubjectService) {
        try {
            EasyExcel.read(multipartFile.getInputStream(),
                            SubjectCategory.class,
                            new SubjectExcelListener(eduSubjectService))
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    @Cacheable(value = "subjectTree")
    public List<SubjectTreeNode> getAllSubject() {
        QueryWrapper<EduSubject> wrapper= new QueryWrapper<>();
        List<EduSubject> subjectList = baseMapper.selectList(wrapper);

        Map<Long, List<SubjectTreeNode>> list = subjectList.parallelStream()
                .collect(groupingBy(EduSubject::getParentId,
                        Collectors.mapping(o -> new SubjectTreeNode(o.getId(), o.getTitle()), Collectors.toList())));
        // the root of subject tree
        List<SubjectTreeNode> tree = list.get(0L);
        tree.forEach(o-> o.setChildren(list.get(o.getId())));
        return tree;
    }

}
