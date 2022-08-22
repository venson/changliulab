package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.chapter.CourseTreeNodeVo;
import com.venson.eduservice.service.EduChapterMarkdownService;
import com.venson.eduservice.service.EduChapterService;
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
    private EduChapterMarkdownService chapterMdService;

    /**
     *  get the course Tree, use chapter and section as tree node
     * @param courseId the id of course
     * @return tree Node List
     */
    @GetMapping("{courseId}")
    public RMessage getCourseTreeByCourseId(@PathVariable Long courseId){
        List<CourseTreeNodeVo> tree = chapterService.getCourseTreeByCourseId(courseId);
        return RMessage.ok().data("tree",tree);
    }

}
