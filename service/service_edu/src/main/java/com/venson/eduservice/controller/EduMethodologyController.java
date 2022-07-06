package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.service.EduMethodologyService;
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
@RequestMapping("/eduservice/edu-methodology")
public class EduMethodologyController {
    @Autowired
    private EduMethodologyService service;

    @GetMapping()
    public RMessage getMethodology(){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.orderByAsc(EduMethodology::getGmtCreate);
        List<EduMethodology> list = service.list(wrapper);
        return RMessage.ok().data("item", list);
    }
    @GetMapping("{id}")
    public RMessage getMethodology(@PathVariable String id){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.eq(EduMethodology::getId, id);
        wrapper.select(EduMethodology::getMarkdown, EduMethodology::getId, EduMethodology::getLanguage);
        EduMethodology methodology = service.getOne(wrapper);
        return RMessage.ok().data("item",methodology);
    }

    @PutMapping("{id}")
    public RMessage updateMethodology(@PathVariable String id, @RequestBody EduMethodology methodology){
        EduMethodology eduMethodology= service.getById(id);
        if(eduMethodology.getPublishRequest()){
            return RMessage.error().message("The Methodology is under request can not be edit");
        }

        methodology.setIsModified(true);
        service.updateById(methodology);
        return RMessage.ok();
    }

    @PostMapping("publish/{id}")
    public RMessage publishRequest(@PathVariable String id){
        EduMethodology methodology = new EduMethodology();
        methodology.setId(id);
        methodology.setPublishRequest(true);
        service.updateById(methodology);
        return RMessage.ok();
    }
    @PutMapping("publish/{id}")
    public RMessage publish(@PathVariable String id){
        EduMethodology methodology = service.getById(id);
        if(!methodology.getPublishRequest()){
            return RMessage.error();
        }
        methodology.setPublishedMd(methodology.getMarkdown());
        methodology.setPublishRequest(false);
        service.updateById(methodology);
        return RMessage.ok();
    }
    @GetMapping("publish")
    public RMessage getPublishList(){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.eq(EduMethodology::getPublishRequest, true);
        List<EduMethodology> list = service.list(wrapper);
        return RMessage.ok().data("item", list);
    }

    @PutMapping("reject/{id}")
    public RMessage reject(@PathVariable String id){
        EduMethodology methodology = new EduMethodology();
        methodology.setId(id);
        methodology.setPublishRequest(false);
        service.updateById(methodology);
        return RMessage.ok();
    }
}
