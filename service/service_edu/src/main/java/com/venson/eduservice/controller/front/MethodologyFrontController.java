package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.service.EduMethodologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eduservice/methodologyFront")
public class MethodologyFrontController {

    @Autowired
    private EduMethodologyService service;
    @GetMapping("{lang}")
    public RMessage getMethodology(@PathVariable String lang){
        LambdaQueryWrapper<EduMethodology> wrapper = new QueryWrapper<EduMethodology>().lambda();
        wrapper.eq(EduMethodology::getLanguage,lang);
        EduMethodology methodology = service.getOne(wrapper);
        return RMessage.ok().data("item", methodology);
    }
}
