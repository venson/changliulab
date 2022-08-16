package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduActivity;
import com.venson.eduservice.entity.EduActivityMarkdown;
import com.venson.eduservice.entity.EduActivityPublishedMd;
import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.enums.ReviewType;
import com.venson.eduservice.entity.vo.ActivityInfoVo;
import com.venson.eduservice.entity.vo.ActivityQuery;
import com.venson.eduservice.entity.vo.ReviewApplyVo;
import com.venson.eduservice.service.EduActivityPublishedMdService;
import com.venson.eduservice.service.EduActivityMarkdownService;
import com.venson.eduservice.service.EduActivityService;
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
    public RMessage getActivity(@PathVariable Long id){
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
        eduActivity.setIsPublished(null);
        activityService.save(eduActivity);
        EduActivityMarkdown markdown = new EduActivityMarkdown();
        markdown.setId(eduActivity.getId());
        markdown.setMarkdown(infoVo.getMarkdown());
        markdownService.save(markdown);
        Long id = eduActivity.getId();
        return RMessage.ok().data("id",id);
    }
    @PutMapping("{id}")
    public RMessage updateActivity(@PathVariable Long id,
                                   @RequestBody ActivityInfoVo infoVo){

        EduActivity activity = activityService.getById(id);
//        if(activity.getReview()==1){
//            return RMessage.error().message("The activity is request for publish");
//        }

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
    public RMessage deleteActivity(@PathVariable Long id){
        publishedMdService.removeById(id);
        markdownService.removeById(id);
        activityService.removeById(id);
        return RMessage.ok();
    }
    @GetMapping("review/{page}/{limit}")
    public RMessage getPageRequestList(@PathVariable Integer page,
                                        @PathVariable Integer limit){
        Map<String, Object> map = activityService.getPageReviewList(page,limit);
        return RMessage.ok().data(map);
    }
    @PostMapping("review/{id}")
    public RMessage requestReviewByActivityId(@PathVariable Long id, @RequestBody ReviewApplyVo reviewVo){
        activityService.requestReviewByActivityId(id, reviewVo);
        return RMessage.ok();
    }
    @PutMapping("review/{id}")
    public RMessage passReviewByActivityId(@PathVariable Long id, @RequestBody ReviewApplyVo reviewVo){
        activityService.passReviewByActivityId(id, reviewVo);
        return RMessage.ok();
    }
    @PostMapping("review/reject/{id}")
    public RMessage rejectReviewByActivityId(@PathVariable Long id, @RequestBody ReviewApplyVo reviewVo) {
        activityService.rejectReviewByActivityId(id, reviewVo);
        return RMessage.ok();
    }
    @PutMapping("hide/{id}")
    public RMessage hideActivityById(@PathVariable Long id){
        activityService.hideActivityById(id);
        return RMessage.ok();
    }



}
