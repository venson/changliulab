package com.venson.eduservice.controller.admin;

import com.venson.commonutils.Result;
import com.venson.eduservice.entity.subject.SubjectTreeNode;
import com.venson.eduservice.service.EduSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public Result addSubject(@RequestPart(value = "file") MultipartFile file){
//         RequestPart value "file" should match the name of upload in frontend
        log.info(file.getOriginalFilename());

        eduSubjectService.saveSubject(file, eduSubjectService);
        return Result.success();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('course.subject.list')")
    public Result getAllSubject(){
        List<SubjectTreeNode> tree = eduSubjectService.getAllSubject();
        return Result.success().data("tree",tree);
    }

}
