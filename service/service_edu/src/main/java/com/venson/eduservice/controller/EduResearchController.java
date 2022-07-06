package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.service.EduResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/eduservice/edu-research")
public class EduResearchController {

    @Autowired
    private EduResearchService service;

    @GetMapping()
    public RMessage getResearch(){
        List<EduResearch> list = service.list();
        return RMessage.ok().data("item", list);
    }
    @GetMapping("{id}")
    public RMessage getResearch(@PathVariable String id){
        LambdaQueryWrapper<EduResearch> wrapper = new QueryWrapper<EduResearch>().lambda();
        wrapper.eq(EduResearch::getId, id);
        wrapper.select(EduResearch::getMarkdown, EduResearch::getId, EduResearch::getLanguage);
        EduResearch research = service.getOne(wrapper);
        return RMessage.ok().data("item",research);
    }

    @PutMapping("{id}")
    public RMessage updateResearch(@PathVariable String id, @RequestBody EduResearch research){
        EduResearch eduResearch= service.getById(id);
        if(eduResearch.getPublishRequest()){
            return RMessage.error().message("The Research is under request can not be edit");
        }

        research.setIsModified(true);
        service.updateById(research);
        return RMessage.ok();
    }

    @PostMapping("publish/{id}")
    public RMessage publishRequest(@PathVariable String id){
        EduResearch research = new EduResearch();
        research.setId(id);
        research.setPublishRequest(true);
        service.updateById(research);
        return RMessage.ok();
    }
    @PutMapping("publish/{id}")
    public RMessage publish(@PathVariable String id){
        EduResearch research = service.getById(id);
        if(!research.getPublishRequest()){
            return RMessage.error();
        }
        research.setPublishedMd(research.getMarkdown());
        research.setPublishRequest(false);
        service.updateById(research);
        return RMessage.ok();
    }
    @GetMapping("publish")
    public RMessage getPublishList(){
        LambdaQueryWrapper<EduResearch> wrapper = new QueryWrapper<EduResearch>().lambda();
        wrapper.eq(EduResearch::getPublishRequest, true);
        List<EduResearch> list = service.list(wrapper);
        return RMessage.ok().data("item", list);
    }

    @PutMapping("reject/{id}")
    public RMessage reject(@PathVariable String id){
        EduResearch research = new EduResearch();
        research.setId(id);
        research.setPublishRequest(false);
        service.updateById(research);
        return RMessage.ok();
    }
}
