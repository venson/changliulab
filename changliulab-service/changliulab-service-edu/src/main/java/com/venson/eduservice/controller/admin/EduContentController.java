package com.venson.eduservice.controller.admin;

import com.venson.commonutils.Result;
import com.venson.eduservice.entity.dto.CourseSyllabusDTO;
import com.venson.eduservice.entity.vo.CourseTreeNodeVo;
import com.venson.eduservice.service.EduChapterService;
import com.venson.eduservice.service.EduContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Only get course chapter and section node tree.
 *  get ,edit and remove chapters are in EduChapterController
 *  get ,edit and remove section are in EduSectionController
 */
@RestController
@RequestMapping("/eduservice/admin/edu-content")
public class EduContentController {

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduContentService contentService;
    /**
     *  get the course Tree, use chapter and section as tree node
     * @param courseId the id of course
     * @return tree Node List
     */
    @GetMapping("{courseId}")
    public Result<List<CourseTreeNodeVo>> getCourseTreeByCourseId(@PathVariable Long courseId){
        List<CourseTreeNodeVo> tree = chapterService.getCourseTreeByCourseId(courseId);
        return Result.success(tree);
    }

    @GetMapping("syllabus/{courseId}")
    public Result<List<CourseSyllabusDTO>> getCourseSyllabusByCourseId(@PathVariable Long courseId){
        List<CourseSyllabusDTO> syllabus =contentService.getSyllabusByCourseId(courseId);
//        List<CourseSyllabusDTO> syllabus = chapterService.getSyllabusByCourseId(courseId);
        return Result.success(syllabus);
    }

}
