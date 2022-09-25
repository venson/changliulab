package com.venson.eduservice.controller.admin;

import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.vo.ScholarFilterVo;
import com.venson.eduservice.service.EduMemberScholarService;
import com.venson.eduservice.service.EduScholarCitationService;
import com.venson.eduservice.service.EduScholarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public Result getScholar(@PathVariable Long id){
        EduScholar scholar = scholarService.getById(id);
        return Result.success().data("item",scholar);

    }

    @PostMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('scholar.list')")
    public Result getPageScholar(@PathVariable Integer page, @PathVariable Integer limit,
                                 @RequestBody(required = false) ScholarFilterVo filterVo){
        return scholarService.getPageScholar(page,limit,filterVo);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('scholar.add')")
    public Result addScholar(@RequestBody EduScholar eduScholar){
        scholarService.save(eduScholar);
        return Result.success().data("id",eduScholar.getId());
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('scholar.edit')")
    public Result updateScholar(@RequestBody EduScholar eduScholar){
        scholarService.updateById(eduScholar);
        return Result.success();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('scholar.remove')")
    public Result deleteScholar(@PathVariable Long id){
        scholarService.removeById(id);
        return Result.success();
    }

//  @
}
