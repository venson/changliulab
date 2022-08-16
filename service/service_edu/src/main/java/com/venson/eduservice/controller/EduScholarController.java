package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduMemberScholar;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.service.EduMemberScholarService;
import com.venson.eduservice.service.EduScholarCitationService;
import com.venson.eduservice.service.EduScholarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/eduservice/edu-scholar")
public class EduScholarController {

    @Autowired
    private EduScholarService scholarService;
    @Autowired
    private EduScholarCitationService scholarCitationService;
    @Autowired
    private EduMemberScholarService memberScholarService;

//get Scholar article info

    @GetMapping("{id}")
    public RMessage getScholar(@PathVariable Long id){
        EduScholar scholar = scholarService.getById(id);
        return RMessage.ok().data("item",scholar);

    }

    @GetMapping("{page}/{limit}")
    public RMessage getPageScholar(@PathVariable Integer page, @PathVariable Integer limit){
        Page<EduScholar> pageScholar = new Page<>(page,limit);
        QueryWrapper<EduScholar> wrapper = new QueryWrapper<>();

        scholarService.getBaseMapper().selectPage(pageScholar, wrapper);
        List<EduScholar> records = pageScholar.getRecords();
        long total = pageScholar.getTotal();
        long size = pageScholar.getSize();
        long current = pageScholar.getCurrent();
        boolean hasPrevious = pageScholar.hasPrevious();
        boolean hasNext = pageScholar.hasNext();
        long pages = pageScholar.getPages();

        HashMap<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        map.put("size",size);
        map.put("current", current);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);
        map.put("pages", pages);
        return RMessage.ok().data(map);
    }

    @PostMapping
    public RMessage addScholar(@RequestBody EduScholar eduScholar){
        scholarService.save(eduScholar);
        return RMessage.ok().data("id",eduScholar.getId());
    }

    @PutMapping("{id}")
    public RMessage updateScholar(@RequestBody EduScholar eduScholar){
        scholarService.updateById(eduScholar);
        return RMessage.ok();
    }

    @DeleteMapping("{id}")
    public RMessage deleteScholar(@PathVariable Long id){
        scholarService.removeById(id);
        return RMessage.ok();
    }

//  @
}
