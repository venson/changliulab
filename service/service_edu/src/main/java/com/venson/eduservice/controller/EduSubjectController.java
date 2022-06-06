package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.subject.LevelISubject;
import com.venson.eduservice.entity.subject.TopSubject;
import com.venson.eduservice.service.EduSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-05-10
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
//@CrossOrigin
@Slf4j
public class EduSubjectController {

    private final EduSubjectService eduSubjectService;

    public EduSubjectController(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @PostMapping("addSubject")
    public RMessage addSubject(@RequestPart(value = "file") MultipartFile file){
//         RequestPart value "file" should match the name of upload in frontend
        log.info(file.getOriginalFilename());

        eduSubjectService.saveSubject(file, eduSubjectService);
        return RMessage.ok();
    }

    @GetMapping("getAllSubject")
    public RMessage getAllSubject(){
        List<TopSubject> allSubject = eduSubjectService.getAllSubject();
        return RMessage.ok().data("list",allSubject);
    }

    @GetMapping("streamtest")
    public RMessage streamtest(){
        Map<String, List<LevelISubject>> stringListMap = eduSubjectService.streamTest();
        return RMessage.ok().data("list",stringListMap);

    }

}
