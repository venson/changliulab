package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.chapter.CourseTreeNodeVo;
import com.venson.eduservice.entity.frontvo.CourseFrontFIlterVo;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.venson.eduservice.entity.frontvo.CourseFrontTreeNodeVo;
import com.venson.eduservice.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
@RequestMapping("/eduservice/front/course")
@Slf4j
public class CourseFrontController {
    @Autowired
    private EduCoursePublishedService coursePublishedService;

    @Autowired
    private EduChapterPublishedService chapterPublishedService;

    @Autowired
    private EduChapterPublishedMdService chapterPublishedMdService;

    @Autowired
    private EduSectionPublishedService sectionPublishedService;

    @Autowired
    private EduSectionPublishedMdService sectionPublishedMdService;



    @PostMapping("course/{page}/{limit}")
    public RMessage getFrontPageCourseList(@PathVariable Integer page,
                                           @PathVariable Integer limit,
                                           @RequestBody(required = false) CourseFrontFIlterVo courseFrontVo){

        Map<String, Object> map =coursePublishedService.getFrontPageCourseList(page,limit,courseFrontVo);
        return RMessage.ok().data(map);
    }

    @GetMapping("course/{id}")
    public RMessage getFrontCourseInfo(@PathVariable Long id){
        CourseFrontInfoVo courseFrontInfoVo = coursePublishedService.getFrontCourseInfo(id);
        List<CourseFrontTreeNodeVo> treeNode = coursePublishedService.getCourseFrontTreeByCourseId(id);
        return RMessage.ok().data("tree",treeNode).data("info",courseFrontInfoVo);
    }
    @GetMapping("chapter/{id}")
    public RMessage getChapterByChapterId(@PathVariable Long id){
        EduChapterPublished chapter = chapterPublishedService.getById(id);
        EduChapterPublishedMd markdown = chapterPublishedMdService.getById(id);
        return RMessage.ok().data("chapter",chapter).data("markdown", markdown);
    }
    @GetMapping("section/{id}")
    public RMessage getSectionBySectionId(@PathVariable Long id){
        EduSectionPublished section = sectionPublishedService.getById(id);
        EduSectionPublishedMd markdown = sectionPublishedMdService.getById(id);
        return RMessage.ok().data("section",section).data("markdown", markdown);
    }
}
