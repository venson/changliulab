package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduActivity;
import com.venson.eduservice.entity.EduActivityMarkdown;
import com.venson.eduservice.entity.EduActivityPublishedMd;
import com.venson.eduservice.entity.vo.ActivityInfoVo;
import com.venson.eduservice.entity.vo.ActivityQuery;
import com.venson.eduservice.service.EduActivityPublishedMdService;
import com.venson.eduservice.service.EduActivityMarkdownService;
import com.venson.eduservice.service.EduActivityService;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
@RequestMapping("/eduservice/edu-activity")
public class EduActivityController {

    @Autowired
    private EduActivityService activityService;

    @Autowired
    private EduActivityPublishedMdService publishedMdService;

    @Autowired
    private EduActivityMarkdownService markdownService;

    @GetMapping("{id}")
    public RMessage getActivity(@PathVariable String id){
        EduActivity eduActivity = activityService.getById(id);
        EduActivityMarkdown markdown = markdownService.getById(id);
        return RMessage.ok().data("activity", eduActivity).data("markdown", markdown);
    }

    @PostMapping("{page}/{limit}")
    public RMessage getPageActivityList(@PathVariable Integer page,
                                        @PathVariable Integer limit,
                                        @RequestBody  ActivityQuery query){
        LambdaQueryWrapper<EduActivity> wrapper = new QueryWrapper<EduActivity>().lambda();
        Page<EduActivity> pageActivity = new Page<>(page, limit);
            String title = query.getTitle();
            String begin = query.getBegin();
            String end = query.getEnd();

        if(!ObjectUtils.isEmpty(title)){
            wrapper.like(EduActivity::getTitle,title);
        }
        if(!ObjectUtils.isEmpty(begin)){
            wrapper.ge(EduActivity::getGmtCreate,begin);
        }
        if(!ObjectUtils.isEmpty(end)){
            wrapper.le(EduActivity::getGmtCreate,end);
        }
        wrapper.orderByDesc(EduActivity::getGmtCreate);
        activityService.page(pageActivity,wrapper);
        Map<String, Object> map = PageUtil.toMap(pageActivity);
        return RMessage.ok().data(map);
    }
    @PostMapping()
    public RMessage addActivity(@RequestBody ActivityInfoVo infoVo){
        EduActivity eduActivity = new EduActivity();
        BeanUtils.copyProperties(infoVo,eduActivity);
        eduActivity.setIsModified(true);
        eduActivity.setPublishRequest(null);
        eduActivity.setIsPublished(null);
        activityService.save(eduActivity);
        EduActivityMarkdown markdown = new EduActivityMarkdown();
        markdown.setId(eduActivity.getId());
        markdown.setMarkdown(infoVo.getMarkdown());
        markdownService.save(markdown);
        return RMessage.ok();
    }
    @PutMapping("{id}")
    public RMessage updateActivity(@PathVariable String id,
                                   @RequestBody ActivityInfoVo infoVo){

        EduActivity activity = activityService.getById(id);
        if(activity.getPublishRequest()){
            return RMessage.error().message("The activity is request for publish");
        }

        EduActivity eduActivity = new EduActivity();
        BeanUtils.copyProperties(infoVo,eduActivity);
        eduActivity.setIsModified(true);
        eduActivity.setIsPublished(null);
        activityService.updateById(eduActivity);

        EduActivityMarkdown markdown = new EduActivityMarkdown();
        markdown.setId(eduActivity.getId());
        markdown.setMarkdown(infoVo.getMarkdown());
        markdownService.updateById(markdown);
        return RMessage.ok();
    }
    @DeleteMapping("{id}")
    public RMessage deleteActivity(@PathVariable String id){
        publishedMdService.removeById(id);
        markdownService.removeById(id);
        activityService.removeById(id);
        return RMessage.ok();
    }
    @GetMapping("publish/{page}/{limit}")
    public RMessage getPageRequestList(@PathVariable Integer page,
                                        @PathVariable Integer limit){
        LambdaQueryWrapper<EduActivity> wrapper = new QueryWrapper<EduActivity>().lambda();
        Page<EduActivity> pageActivity = new Page<>(page, limit);
        wrapper.orderByDesc(EduActivity::getGmtCreate);
        wrapper.eq(EduActivity::getPublishRequest,true);
        activityService.page(pageActivity,wrapper);
        Map<String, Object> map = PageUtil.toMap(pageActivity);
        return RMessage.ok().data(map);
    }
    @PostMapping("publish/{id}")
    public RMessage publishRequest(@PathVariable String id){
        EduActivity activity = activityService.getById(id);
        activity.setPublishRequest(true);
        activityService.updateById(activity);
        String activityId = activity.getId();
        return RMessage.ok().data("item",activityId);
    }
    @PutMapping("publish/{id}")
    public RMessage publishActivity(@PathVariable String id){
        EduActivity eduActivity = activityService.getById(id);
        EduActivityMarkdown markdown = markdownService.getById(id);
        if(!eduActivity.getPublishRequest() || ObjectUtils.isEmpty(markdown.getMarkdown())){
            return RMessage.error();
        }
        eduActivity.setIsPublished(true);
        eduActivity.setIsModified(false);
        EduActivityPublishedMd publishedMd = new EduActivityPublishedMd();
        publishedMd.setId(eduActivity.getId());
        publishedMd.setPublishedMd(markdown.getMarkdown());
        activityService.updateById(eduActivity);
        publishedMdService.saveOrUpdate(publishedMd);
        return RMessage.ok();
    }
    @DeleteMapping("publish/{id}")
    public RMessage rejectPublish(@PathVariable String id) {
        EduActivity activity = activityService.getById(id);
        if(!activity.getPublishRequest()){
            return RMessage.error();
        }
        activity.setPublishRequest(false);
        activityService.updateById(activity);

        return RMessage.ok();

    }



}
