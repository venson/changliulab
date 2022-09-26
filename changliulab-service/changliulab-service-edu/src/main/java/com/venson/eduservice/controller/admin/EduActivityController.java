package com.venson.eduservice.controller.admin;

import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduActivity;
import com.venson.eduservice.entity.EduActivityMarkdown;
import com.venson.eduservice.entity.dto.ActivityInfoVo;
import com.venson.eduservice.entity.dto.ActivityQuery;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.service.EduActivityMarkdownService;
import com.venson.eduservice.service.EduActivityPublishedMdService;
import com.venson.eduservice.service.EduActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/eduservice/admin/edu-activity")
public class EduActivityController {

    @Autowired
    private EduActivityService activityService;

    @Autowired
    private EduActivityPublishedMdService publishedMdService;

    @Autowired
    private EduActivityMarkdownService markdownService;

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('activity.edit', 'activity.review.pass', 'activity.review.reject','activity.review')")
    public Result getActivity(@PathVariable Long id){
        EduActivity eduActivity = activityService.getById(id);
        EduActivityMarkdown markdown = markdownService.getById(id);
        return Result.success().data("activity", eduActivity).data("markdown", markdown);
    }

    @PostMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('activity.list')")
    public Result getPageActivityList(@PathVariable Integer page,
                                      @PathVariable Integer limit,
                                      @RequestBody ActivityQuery query){
        Map<String,Object> map = activityService.getPageActivityList(page,limit,query);
        return Result.success(map);
    }
    @PostMapping()
    @PreAuthorize("hasAuthority('activity.add')")
    public Result newEmptyActivity(){
        Long id = activityService.newEmptyActivity();
        return Result.success().data("id",id);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('activity.edit')")
    public Result updateActivity(@PathVariable Long id,
                                 @RequestBody ActivityInfoVo infoVo){
        activityService.updateActivity(id,infoVo);
        return Result.success();
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('activity.remove')")
    public Result deleteActivity(@PathVariable Long id){
        activityService.deleteActivity(id);
        return Result.success();
    }
    @GetMapping("review/{page}/{limit}")
    @PreAuthorize("hasAuthority('activity.review')")
    public Result getPageRequestList(@PathVariable Integer page,
                                     @PathVariable Integer limit){
        Map<String, Object> map = activityService.getPageReviewList(page,limit);
        return Result.success().data(map);
    }
    @PostMapping("review/{id}")
    @PreAuthorize("hasAuthority('activity.review.request')")
    public Result requestReviewByActivityId(@PathVariable Long id, @RequestBody ReviewApplyVo reviewVo){
        activityService.requestReviewByActivityId(id, reviewVo);
        return Result.success();
    }
    @PutMapping("review/{id}")
    @PreAuthorize("hasAuthority('activity.review.pass')")
    public Result passReviewByActivityId(@PathVariable Long id, @RequestBody ReviewApplyVo reviewVo){
        activityService.passReviewByActivityId(id, reviewVo);
        return Result.success();
    }
    @PostMapping("review/reject/{id}")
    @PreAuthorize("hasAuthority('activity.review.reject')")
    public Result rejectReviewByActivityId(@PathVariable Long id, @RequestBody ReviewApplyVo reviewVo) {
        activityService.rejectReviewByActivityId(id, reviewVo);
        return Result.success();
    }
    @PutMapping("hide/{id}")
    @PreAuthorize("hasAuthority('activity.review.edit')")
    public Result hideActivityById(@PathVariable Long id){
        activityService.hideActivityById(id);
        return Result.success();
    }



}
