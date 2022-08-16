package com.venson.eduservice.controller.front;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduActivityPublished;
import com.venson.eduservice.entity.EduActivityPublishedMd;
import com.venson.eduservice.service.EduActivityPublishedMdService;
import com.venson.eduservice.service.EduActivityPublishedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("eduservice/front/activity")
public class ActivityFrontController {
    @Autowired
    private EduActivityPublishedService publishedService;

    @Autowired
    private EduActivityPublishedMdService publishedMdService;

    @GetMapping("{page}/{limit}")
    public RMessage getPageActivity(@PathVariable Integer page, @PathVariable Integer limit){
        Map<String, Object> map = publishedService.getPageActivityList(page, limit);
        return RMessage.ok().data(map);
    }
    @GetMapping("{id}")
    public RMessage getActivityById(@PathVariable Long id){
        EduActivityPublished activity = publishedService.getById(id);
        EduActivityPublishedMd markdown= publishedMdService.getById(id);
        return RMessage.ok().data("activity",activity).data("markdown",markdown);
    }
}
