package com.venson.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduMemberScholar;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.vo.ScholarFilterVo;
import com.venson.eduservice.service.EduMemberScholarService;
import com.venson.eduservice.service.EduScholarCitationService;
import com.venson.eduservice.service.EduScholarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/eduservice/admin/edu-scholar")
public class EduScholarController {

    @Autowired
    private EduScholarService scholarService;
    @Autowired
    private EduScholarCitationService scholarCitationService;
    @Autowired
    private EduMemberScholarService memberScholarService;

//get Scholar article info

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('scholar.edit')")
    public RMessage getScholar(@PathVariable Long id){
        EduScholar scholar = scholarService.getById(id);
        return RMessage.ok().data("item",scholar);

    }

    @GetMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('scholar.list')")
    public RMessage getPageScholar(@PathVariable Integer page, @PathVariable Integer limit,
                                   @RequestBody ScholarFilterVo filterVo){
        return scholarService.getPageScholar(page,limit,filterVo);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('scholar.add')")
    public RMessage addScholar(@RequestBody EduScholar eduScholar){
        scholarService.save(eduScholar);
        return RMessage.ok().data("id",eduScholar.getId());
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('scholar.edit')")
    public RMessage updateScholar(@RequestBody EduScholar eduScholar){
        scholarService.updateById(eduScholar);
        return RMessage.ok();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('scholar.remove')")
    public RMessage deleteScholar(@PathVariable Long id){
        scholarService.removeById(id);
        return RMessage.ok();
    }

//  @
}
