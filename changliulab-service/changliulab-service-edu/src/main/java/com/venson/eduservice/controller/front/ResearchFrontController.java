package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduResearch;
import com.venson.eduservice.entity.enums.LanguageEnum;
import com.venson.eduservice.service.EduResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eduservice/front/research")
public class ResearchFrontController {

    @Autowired
    private EduResearchService service;
    @GetMapping("{lang}")
    public Result<EduResearch> getResearch(@PathVariable LanguageEnum lang){
        LambdaQueryWrapper<EduResearch> wrapper = new QueryWrapper<EduResearch>().lambda();
        wrapper.eq(EduResearch::getLanguage,lang).eq(EduResearch::getEnable,true);
        wrapper.select(EduResearch::getPublishedHtmlBrBase64, EduResearch::getLanguage);
        EduResearch research= service.getOne(wrapper);

        return Result.success(research);
    }
}
