package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.eduservice.entity.EduTeacher;
import com.venson.eduservice.mapper.EduTeacherMapper;
import com.venson.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-02
 */
@Service
public class EduTeacherServiceImp extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(teacherPage,wrapper);

        List<EduTeacher> records = teacherPage.getRecords();

        long total = teacherPage.getTotal();
        long pages = teacherPage.getPages();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("pages", pages);
        map.put("total", total);
        map.put("current", current);
        map.put("size",size);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);



        return map;
    }
}
