package com.venson.eduservice.controller.front;

import com.venson.commonutils.PageResponse;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduActivityPublished;
import com.venson.eduservice.entity.front.dto.ActivityFrontDTO;
import com.venson.eduservice.service.front.ActivityFrontService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eduservice/front/activity")
public class ActivityFrontController {

    private final ActivityFrontService activityFrontService;

    public ActivityFrontController(ActivityFrontService activityFrontService) {
        this.activityFrontService = activityFrontService;
    }

    @GetMapping("{page}/{limit}")
    public Result<PageResponse<EduActivityPublished>> getPageActivity(@PathVariable Integer page, @PathVariable Integer limit){
        PageResponse<EduActivityPublished> pageRes = activityFrontService.getPageActivity(page, limit);
        return Result.success(pageRes);
    }
    @GetMapping("{id}")
    public Result<ActivityFrontDTO> getActivityById(@PathVariable Long id){
        ActivityFrontDTO activity = activityFrontService.getActivityById(id);
        return Result.success(activity);
    }
}
