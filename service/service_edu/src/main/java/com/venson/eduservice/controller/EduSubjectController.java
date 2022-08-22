package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.subject.LevelISubject;
import com.venson.eduservice.entity.subject.SubjectTreeNode;
import com.venson.eduservice.service.EduSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/eduservice/admin/edu-subject")
//@CrossOrigin
@Slf4j
public class EduSubjectController {

    private final EduSubjectService eduSubjectService;

    public EduSubjectController(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @PostMapping("")
    public RMessage addSubject(@RequestPart(value = "file") MultipartFile file){
//         RequestPart value "file" should match the name of upload in frontend
        log.info(file.getOriginalFilename());

        eduSubjectService.saveSubject(file, eduSubjectService);
        return RMessage.ok();
    }

    @GetMapping("")
    @PreAuthorize("hasAthority('subject.list')")
    public RMessage getAllSubject(){
        List<SubjectTreeNode> tree = eduSubjectService.getAllSubject();
        return RMessage.ok().data("tree",tree);
    }

}
