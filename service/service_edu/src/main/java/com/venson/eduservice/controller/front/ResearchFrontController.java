package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.service.EduResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eduservice/researchFront")
public class ResearchFrontController {

    @Autowired
    private EduResearchService service;
    @GetMapping("{lang}")
    public RMessage getMethodology(@PathVariable String lang){
        LambdaQueryWrapper<EduResearch> wrapper = new QueryWrapper<EduResearch>().lambda();
        wrapper.eq(EduResearch::getLanguage,lang);
        EduResearch research= service.getOne(wrapper);
        return RMessage.ok().data("item",research);
    }
}
