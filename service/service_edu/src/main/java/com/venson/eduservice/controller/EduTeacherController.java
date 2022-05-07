package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduTeacher;
import com.venson.eduservice.entity.vo.TeacherQuery;
import com.venson.eduservice.service.EduTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-05-02
 */
@Slf4j
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    private final EduTeacherService teacherService;

    @Autowired
    public EduTeacherController(EduTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("findAll")
    public RMessage findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        log.info(list.toString());
        return RMessage.ok().data("items", list);

    }

    @DeleteMapping("{id}")
    public RMessage removeTeacher(@PathVariable("id") String id){
        boolean result = teacherService.removeById(id);
        if (result){
            return RMessage.ok();
        } else return RMessage.error();
    }


    @GetMapping("teacherPage/{current}/{recordPerPage}")
    public RMessage teacherPageList(@PathVariable Integer current,
                                    @PathVariable Integer recordPerPage){
        Page<EduTeacher> page = new Page<>(current, recordPerPage);
        log.info(page.toString());
        teacherService.page(page,null);
        log.info("------------");
        log.info(page.toString());
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return RMessage.ok().data("total",total).data("records", records);

    }


    @PostMapping("pageTeacherCondition/{current}/{recordPerPage}")
    public RMessage pageTeacherCondition(@PathVariable Integer current,
                                         @PathVariable Integer recordPerPage,
                                         @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>(current, recordPerPage);
        LambdaQueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>().lambda();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!ObjectUtils.isEmpty(name)){
            wrapper.like(EduTeacher::getName,name);
        }
        if(!ObjectUtils.isEmpty(level)){
            wrapper.eq(EduTeacher::getLevel,level);
        }
        if(!ObjectUtils.isEmpty(begin)){
            wrapper.ge(EduTeacher::getGmtCreate, begin);
        }
        if(!ObjectUtils.isEmpty(end)){
            wrapper.le(EduTeacher::getGmtCreate, end);
        }

        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return RMessage.ok().data("total",total).data("row", records);

    }


    @PostMapping("addTeacher")
    public RMessage addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return RMessage.ok();
        }else {
            return RMessage.error();
        }
    }

    @GetMapping("getTeacher/{id}")
    public RMessage getTeacher(@PathVariable Integer id){
        EduTeacher teacher = teacherService.getById(id);
        if (!ObjectUtils.isEmpty(teacher)){
            return RMessage.ok().data("teacher", teacher);
        }else{
            return RMessage.error();
        }
    }


    @PostMapping("updateTeacher")
    public RMessage updateTeacher(@RequestBody EduTeacher teacher){
        boolean successFlag = teacherService.updateById(teacher);
        return successFlag? RMessage.ok() : RMessage.error();
    }

    @PutMapping("{id}")
    public RMessage updateTeacherPut(@PathVariable String id,
                                     @RequestBody EduTeacher teacher){
        teacher.setId(id);
        boolean successFlag = teacherService.updateById(teacher);
        return successFlag? RMessage.ok() : RMessage.error();
    }



}
