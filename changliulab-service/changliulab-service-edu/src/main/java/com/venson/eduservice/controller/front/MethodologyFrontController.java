package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.entity.enums.LanguageEnum;
import com.venson.eduservice.service.EduMethodologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eduservice/front/methodology")
@Slf4j
public class MethodologyFrontController {

    @Autowired
    private EduMethodologyService service;
    @GetMapping("{lang}")
    public Result<EduMethodology> getMethodology(@PathVariable LanguageEnum lang){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.eq(EduMethodology::getLanguage,lang).eq(EduMethodology::getEnable, true);
        wrapper.select(EduMethodology::getPublishedHtmlBrBase64, EduMethodology::getLanguage);
        EduMethodology methodology = service.getOne(wrapper);
        return Result.success(methodology);
    }
}
