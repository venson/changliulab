package com.venson.eduservice.controller.front;

import com.venson.commonutils.PageResponse;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.front.dto.ScholarFrontDTO;
import com.venson.eduservice.entity.front.vo.CitationFrontVo;
import com.venson.eduservice.entity.front.vo.ScholarFrontFilterVo;
import com.venson.eduservice.service.front.ScholarFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("eduservice/front/scholar")
public class ScholarFrontController {

    @Autowired
    private ScholarFrontService scholarFrontService;

    @PostMapping("{pageNum}/{limit}")
    public Result<PageResponse<EduScholar>> getPageScholar(@PathVariable Integer pageNum, @PathVariable Integer limit,
                                                           @RequestBody(required = false) ScholarFrontFilterVo filterVo){

        PageResponse<EduScholar> pageRes = scholarFrontService.getPageScholarWithFilter(pageNum, limit, filterVo);
        return Result.success(pageRes);
    }

    @GetMapping("{scholarId}")
    public Result<ScholarFrontDTO> getAllByScholarId(@PathVariable String scholarId){
        ScholarFrontDTO scholar = scholarFrontService.getAllByScholarId(scholarId);
        return Result.success(scholar);
    }

    /**
     * return citation static by memberId
     * @param memberId the ID of member
     * @return Map<String, IntSummaryStatics>
     */
    @GetMapping("citation/{memberId}")
    @Cacheable(value = "citation:memberId",key = "#memberId")
    public Result<CitationFrontVo> getCitationByMemberId(@PathVariable Long memberId){
        CitationFrontVo citation = scholarFrontService.getCitationByMemberId(memberId);
        return Result.success(citation);

    }

    @GetMapping("{memberId}/{pageNum}/{limit}")
    public Result<PageResponse<EduScholar>> getPageScholarByMemberId(@PathVariable Long memberId,
                                           @PathVariable Integer pageNum, @PathVariable Integer limit){

        PageResponse<EduScholar> pageRes = scholarFrontService.getPageScholarByMemberId(memberId, pageNum, limit);
        return Result.success(pageRes);
    }

}
