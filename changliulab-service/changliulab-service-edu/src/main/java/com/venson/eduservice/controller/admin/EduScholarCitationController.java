package com.venson.eduservice.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduScholarCitation;
import com.venson.eduservice.service.EduScholarCitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/eduservice/admin/edu-scholar-citation")
public class EduScholarCitationController {
    @Autowired
    private EduScholarCitationService citationService;

    @GetMapping("{scholarId}")
    public Result getCitationByScholarId(@PathVariable String scholarId){
        List<EduScholarCitation> list = citationService.list(
                new QueryWrapper<EduScholarCitation>()
                        .select("year", "citations", "scholar_id")
                        .eq("scholar_id", scholarId));
        return  Result.success().data("item",list);
    }

    @PostMapping()
    public Result newScholarCitation(@RequestBody List<EduScholarCitation> citationList){
        citationService.saveBatch(citationList);
        return Result.success();
    }

    @PutMapping("{scholarId}")
    public Result updateCitation(@PathVariable String scholarId, @RequestBody List<EduScholarCitation> citationList){

        citationService.remove(new QueryWrapper<EduScholarCitation>().eq("scholar_id", scholarId));
        citationService.saveBatch(citationList);
        return Result.success();
    }

    @DeleteMapping("{scholarId}")
    public Result deleteCitationByScholarId(@PathVariable String scholarId){
        citationService.remove(new QueryWrapper<EduScholarCitation>().eq("scholar_id", scholarId));
        return Result.success();
    }


}
