package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduChapterSectionContent;
import com.venson.eduservice.service.EduChapterSectionContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-06-14
 */
@RestController
@RequestMapping("/eduservice/edu-content")
public class EduChapterSectionContentController {

    @Autowired
    private EduChapterSectionContentService contentService;

    @GetMapping("chapter/{id}")
    public RMessage getChapterContent(@PathVariable String id){
        QueryWrapper<EduChapterSectionContent> wrapper = new QueryWrapper<>();
        EduChapterSectionContent content = contentService.getChapterById(id);
        return RMessage.ok().data("item",content);
    }

    @GetMapping("section/{id}")
    public RMessage getSectionContent(@PathVariable String id){
        EduChapterSectionContent content = contentService.getSectionById(id);
        return RMessage.ok().data("item",content);
    }

    @PutMapping("{id}")
    public RMessage updateContent(@PathVariable String id, @RequestBody EduChapterSectionContent content){
        contentService.updateById(content);
        return RMessage.ok();
    }
    @PostMapping
    public RMessage saveContent(@RequestBody EduChapterSectionContent content){
        contentService.save(content);
        return RMessage.ok();
    }
}
