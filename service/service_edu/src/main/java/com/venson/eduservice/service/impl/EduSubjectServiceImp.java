package com.venson.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.eduservice.entity.EduSubject;
import com.venson.eduservice.entity.excel.SubjectCategory;
import com.venson.eduservice.entity.subject.LevelISubject;
import com.venson.eduservice.entity.subject.TopSubject;
import com.venson.eduservice.listener.SubjectExcelListener;
import com.venson.eduservice.mapper.EduSubjectMapper;
import com.venson.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public List<TopSubject> getAllSubject() {
        QueryWrapper<EduSubject> wrapperTop= new QueryWrapper<>();
        wrapperTop.eq("parent_id",0);
        List<EduSubject> topSubjectList = baseMapper.selectList(wrapperTop);
        QueryWrapper<EduSubject> wrapperLevelI = new QueryWrapper<>();

        wrapperLevelI.ne("parent_id",0);
        List<EduSubject> levelISubjectList = baseMapper.selectList(wrapperLevelI);
        List<TopSubject> topSubject = new ArrayList<>();
// method 1
//        TopSubject topSubjectTemp = new TopSubject();
//        for (int i = 0; i < topSubjectList.size()-1; i++) {
//            EduSubject top = topSubjectList.get(i);
//            topSubjectTemp.setId(top.getId());
//            topSubjectTemp.setTitle(top.getTitle());
//            List<LevelISubject> levelISubject = new ArrayList<>();
//            for (int j = 0; j < levelISubjectList.size() - 1; j++) {
//                EduSubject levelI = levelISubjectList.get(j);
//                if(top.getId().equals(levelI.getParentId())){
//                    LevelISubject temp = new LevelISubject();
//                    temp.setId(levelI.getId());
//                    temp.setTitle(levelI.getTitle());
//                    levelISubject.add(temp);
//                }
//                topSubjectTemp.setChildren(levelISubject);
//            }
//
//            topSubject.add(topSubjectTemp);
//        }


//        method2
        for (EduSubject eduSubject :
                topSubjectList) {
            TopSubject top = new TopSubject();
            top.setId(eduSubject.getId());
            top.setTitle(eduSubject.getTitle());
            for (EduSubject edu :
                    levelISubjectList) {
                LevelISubject temp = new LevelISubject();
                if(Objects.equals(top.getId(),edu.getParentId())){
                    temp.setTitle(edu.getTitle());
                    temp.setId(edu.getId());
                    top.getChildren().add(temp);
                }
            }
            topSubject.add(top);

        }

        return topSubject;
    }

    @Override
    public Map<Long, List<LevelISubject>> streamTest() {

        QueryWrapper<EduSubject> wrapperTop= new QueryWrapper<>();
        wrapperTop.eq("parent_id",0);
        List<EduSubject> topSubjectList = baseMapper.selectList(wrapperTop);
        QueryWrapper<EduSubject> wrapperLevelI = new QueryWrapper<>();

        wrapperLevelI.ne("parent_id",0);
        List<EduSubject> levelISubjectList = baseMapper.selectList(wrapperLevelI);
        List<TopSubject> topSubject = new ArrayList<>();

        List<EduSubject> allList = baseMapper.selectList(null);


        return levelISubjectList.parallelStream().
                collect(groupingBy(EduSubject::getParentId,
                        Collectors.mapping(EduSubject -> new LevelISubject(EduSubject.getId(), EduSubject.getTitle()),
                                Collectors.toList())));

    }
}
