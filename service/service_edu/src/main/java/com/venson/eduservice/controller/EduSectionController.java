package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduSection;
import com.venson.eduservice.service.EduSectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/eduservice/edu-section")
@Slf4j
public class EduSectionController {


    private final EduSectionService eduSectionService;

    public EduSectionController(EduSectionService eduSectionService) {
        this.eduSectionService = eduSectionService;
    }

    @PostMapping()
    public RMessage addSection(@RequestBody EduSection eduSection) {
        eduSectionService.save(eduSection);
        String id = eduSection.getId();
        return RMessage.ok().data("id",id);
    }


    @DeleteMapping("{id}")
    public RMessage deleteSection(@PathVariable String id) {
        eduSectionService.removeById(id);
        return RMessage.ok();
    }

    @PutMapping("{id}")
    public RMessage updateSection(@RequestBody EduSection eduSection) {
        log.info("starting put");
        eduSectionService.updateById(eduSection);
        return RMessage.ok();
    }

    @GetMapping("{id}")
    public RMessage getSection(@PathVariable String id) {
        EduSection Section = eduSectionService.getById(id);
        return RMessage.ok().data("item", Section);
    }
}
