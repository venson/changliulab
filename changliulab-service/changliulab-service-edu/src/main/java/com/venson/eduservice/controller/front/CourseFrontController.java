package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.frontvo.CourseFrontFIlterVo;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoDTO;
import com.venson.eduservice.entity.frontvo.CourseFrontTreeNodeVo;
import com.venson.eduservice.entity.subject.SubjectTreeNode;
import com.venson.eduservice.service.*;
import com.venson.eduservice.service.front.CourseFrontService;
import com.venson.eduservice.service.front.ScholarFrontService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/front/course")
@Slf4j
public class CourseFrontController {
    @Autowired
    private CourseFrontService courseFrontService;
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

    @Autowired
    private EduSubjectService subjectService;




    @PostMapping("{page}/{limit}")
    public Result getFrontPageCourseList(@PathVariable Integer page,
                                         @PathVariable Integer limit,
                                         @RequestBody(required = false) CourseFrontFIlterVo courseFrontVo){

        Map<String, Object> map = coursePublishedService.getFrontPageCourseList(page, limit, courseFrontVo);
        return Result.success().data(map);
    }
    // TODO redis view count
//    @PostMapping("{id}")
//    public RMessage courseViewCount(@PathVariable Long id){
//        String key = String.join(":","course","view",id.toString());
//        List<Object> values = redisTemplate.opsForHash().values(key);
//        redisTemplate.opsForValue().increment("course:view:"+id);
//        return RMessage.success();
//    }

    @GetMapping("{id}")
    public Result getFrontCourseInfo(@PathVariable Long id){
        CourseFrontInfoDTO courseInfoDTO = courseFrontService.getFrontCourseInfo(id);
        List<CourseFrontTreeNodeVo> treeNode = coursePublishedService.getCourseFrontTreeByCourseId(id);
        return Result.success().data("tree",treeNode).data("info", courseInfoDTO);
    }
    @GetMapping("chapter/{id}")
    public Result getChapterByChapterId(@PathVariable Long id){

        EduChapterPublished chapter = chapterPublishedService.getById(id);
        EduChapterPublishedMd markdown = chapterPublishedMdService.getById(id);
        return Result.success().data("chapter",chapter).data("markdown", markdown);
    }
    @GetMapping("section/{id}")
    public Result getSectionBySectionId(@PathVariable Long id){
        EduSectionPublished section = sectionPublishedService.getById(id);
        EduSectionPublishedMd markdown = sectionPublishedMdService.getById(id);
        return Result.success().data("section",section).data("markdown", markdown);
    }
    @GetMapping("subject")
    public Result getAllSubject(){
        List<SubjectTreeNode> tree = subjectService.getAllSubject();

        return Result.success().data(tree);
    }
}
