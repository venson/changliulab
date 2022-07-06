package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduActivity;
import com.venson.eduservice.entity.EduActivityPublishedMd;
import com.venson.eduservice.service.EduActivityPublishedMdService;
import com.venson.eduservice.service.EduActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("eduservice/activityFront")
public class ActivityFrontController {
    @Autowired
    private EduActivityService activityService;

    @Autowired
    private EduActivityPublishedMdService htmlService;

    @GetMapping("{page}/{limit}")
    public RMessage getPageActivity(@PathVariable Integer page, @PathVariable Integer limit){
        Page<EduActivity> pageActivity = new Page<>(page, limit);
        LambdaQueryWrapper<EduActivity> wrapper = new QueryWrapper<EduActivity>().lambda();
        wrapper.orderByDesc(EduActivity::getActivityDate);
        activityService.page(pageActivity, wrapper);
        Map<String, Object> map = PageUtil.toMap(pageActivity);
        return RMessage.ok().data(map);
    }
    @GetMapping("{id}")
    public RMessage getActivity(@PathVariable String id){
        EduActivity activity = activityService.getById(id);
        EduActivityPublishedMd html = htmlService.getById(id);
        return RMessage.ok().data("activity",activity).data("html",html);
    }
}
