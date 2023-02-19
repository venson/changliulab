package com.venson.eduservice.controller.front;

import com.venson.commonutils.PageResponse;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.*;
import com.venson.eduservice.entity.front.dto.ChapterFrontDTO;
import com.venson.eduservice.entity.front.dto.SectionFrontDTO;
import com.venson.eduservice.entity.front.vo.CourseFrontFilterVo;
import com.venson.eduservice.entity.front.vo.CourseFrontInfoDTO;
import com.venson.eduservice.entity.front.vo.CourseFrontTreeNodeVo;
import com.venson.eduservice.entity.subject.SubjectTreeNode;
import com.venson.eduservice.service.*;
import com.venson.eduservice.service.front.CourseFrontService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eduservice/front/course")
@Slf4j
public class CourseFrontController {
    @Autowired
    private CourseFrontService courseFrontService;
    @Autowired
    private EduCoursePublishedService coursePublishedService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Autowired
    private EduSubjectService subjectService;

    @GetMapping("{id}/{page}/{limit}")
    public Result<PageResponse<EduCoursePublished>> getFrontPageCourseByMemberId(@PathVariable Long id,
                                               @PathVariable Integer page,
                                               @PathVariable Integer limit){
        PageResponse<EduCoursePublished> pageRes = courseFrontService.getPageCourseByMemberId(id, page, limit);
        return Result.success(pageRes);
    }



    @PostMapping("{page}/{limit}")
    public Result<PageResponse<EduCoursePublished>> getFrontPageCourseList(@PathVariable Integer page,
                                                                           @PathVariable Integer limit,
                                                                           @RequestBody(required = false) CourseFrontFilterVo courseFrontVo){

        PageResponse<EduCoursePublished> pageRes = coursePublishedService.getFrontPageCourseList(page, limit, courseFrontVo);
        return Result.success(pageRes);
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
    public Result<CourseFrontInfoDTO> getFrontCourseInfo(@PathVariable Long id){
        CourseFrontInfoDTO courseInfoDTO = courseFrontService.getFrontCourseInfo(id);
        return Result.success(courseInfoDTO);
    }
    @GetMapping("chapter/{id}")
    @Deprecated
    public Result<ChapterFrontDTO> getChapterByChapterId(@PathVariable Long id){
        ChapterFrontDTO chapter = courseFrontService.getChapterByChapterId(id);

        return Result.success(chapter);
    }
    @GetMapping("section/{id}")
    @PostAuthorize("returnObject.data.isPublic or hasAuthority('user')")
    public Result<SectionFrontDTO> getSectionBySectionId(@PathVariable Long id){
        SectionFrontDTO section = courseFrontService.getSectionBySectionId(id);
        return Result.success(section);
    }
    @GetMapping("tree/{id}")
    public Result<List<CourseFrontTreeNodeVo>> getCourseTreeById(@PathVariable Long id){
        List<CourseFrontTreeNodeVo> tree = courseFrontService.getCourseFrontTreeByCourseId(id);
        assert tree.size()>0;
        String redisKey = "course:counter:" + id;
        CourseFrontInfoDTO frontCourseInfo = courseFrontService.getFrontCourseInfo(id);
        stringRedisTemplate.opsForValue().setIfAbsent(redisKey,frontCourseInfo.getViewCount().toString());
        stringRedisTemplate.opsForValue().increment(redisKey);
        return Result.success(tree);
    }
//    @GetMapping("syllabus/{courseId}")
//    public Result<List<CourseSyllabusFrontDTO>> getCourseSyllabusByCourseId(@PathVariable Long courseId){
//        List<CourseSyllabusFrontDTO> syllabus = courseFrontService.getSyllabusByCourseId(courseId);
////        List<CourseSyllabusDTO> syllabus = chapterService.getSyllabusByCourseId(courseId);
//        return Result.success(syllabus);
//    }
    @GetMapping("subject")
    public Result<List<SubjectTreeNode>> getAllSubject(){
        List<SubjectTreeNode> tree = subjectService.getAllSubject();

        return Result.success(tree);
    }
}
