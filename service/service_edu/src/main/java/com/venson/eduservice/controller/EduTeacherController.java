package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduTeacher;
import com.venson.eduservice.service.EduTeacherService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

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

}
