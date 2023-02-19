package com.venson.eduservice.controller.admin;

import com.venson.commonutils.PageResponse;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduActivity;
import com.venson.eduservice.entity.dto.ActivityDTO;
import com.venson.eduservice.entity.dto.ActivityPreviewDTO;
import com.venson.eduservice.entity.enums.PageType;
import com.venson.eduservice.service.EduActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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


    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('activity.edit', 'activity.review.pass', 'activity.review.reject','activity.review')")
    public Result<ActivityDTO> getActivity(@PathVariable Long id){
        ActivityDTO activity = activityService.getActivityById(id);
        return Result.success(activity);
    }

//    @PostMapping(value = "{page}/{limit}", params={"type", "title", "begin", "end"})
    @GetMapping(value = "{page}/{limit}", params={"type"})
    @PreAuthorize("hasAuthority('activity.list')")
    public Result<PageResponse<EduActivity>> getPageActivityList(@PathVariable Integer page,
                                      @PathVariable Integer limit,
                                                                 @RequestParam PageType type,
                                                                 @RequestParam(required = false) String title,
                                                                 @RequestParam(required = false) String begin,
                                                                 @RequestParam(required = false) String end){
        PageResponse<EduActivity> pageRes;
        if( type == PageType.REVIEW){
            pageRes = activityService.getPageReviewList(page,limit);
        }else{
            pageRes = activityService.getPageActivityList(page, limit, title, begin, end);
        }
        return Result.success(pageRes);
    }
    @PostMapping()
    @PreAuthorize("hasAuthority('activity.add')")
    public Result<Long> addActivity(@Valid @RequestBody ActivityDTO activityDTO){
        Long id = activityService.addActivity(activityDTO);
        return Result.success(id);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('activity.edit')")
    public Result<String> updateActivity(@PathVariable Long id,
                                 @Valid @RequestBody ActivityDTO activityDTO){
        activityService.updateActivity(id,activityDTO);
        return Result.success();
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('activity.remove')")
    public Result<String> deleteActivity(@PathVariable Long id){
        activityService.deleteActivity(id);
        return Result.success();
    }
    @GetMapping("review/{page}/{limit}")
    @PreAuthorize("hasAuthority('activity.review')")
    public Result<PageResponse<EduActivity>> getPageRequestList(@PathVariable Integer page,
                                                   @PathVariable Integer limit){
        PageResponse<EduActivity> pageRes = activityService.getPageReviewList(page, limit);
        return Result.success(pageRes);
    }
    @GetMapping("preview/{id}")
    public Result<ActivityPreviewDTO> getActivityPreview(@PathVariable long id){
        ActivityPreviewDTO preview = activityService.getPreviewByActivityId(id);
        return Result.success(preview);
    }
    @PutMapping("enable/{id}")
    @PreAuthorize("hasAuthority('activity.enable')")
    public Result<String> switchActivityById(@PathVariable Long id){
        activityService.switchEnableByActivityId(id);
        return Result.success();
    }



}
