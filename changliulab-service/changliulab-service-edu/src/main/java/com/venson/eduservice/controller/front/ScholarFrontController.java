package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.EduMemberScholar;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.EduScholarCitation;
import com.venson.eduservice.entity.frontvo.ScholarFrontFilterVo;
import com.venson.eduservice.service.EduMemberScholarService;
import com.venson.eduservice.service.EduMemberService;
import com.venson.eduservice.service.EduScholarCitationService;
import com.venson.eduservice.service.EduScholarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("eduservice/front/scholar")
public class ScholarFrontController {

    @Autowired
    private EduScholarService eduScholarService;
    @Autowired
    private EduScholarCitationService citationService;
    @Autowired
    private EduMemberScholarService memberScholarService;
    @Autowired
    private EduMemberService memberService;

    @PostMapping("{pageNum}/{limit}")
    public Result getPageScholar(@PathVariable Integer pageNum, @PathVariable Integer limit,
                                 @RequestBody ScholarFrontFilterVo filterVo){

        Page<EduScholar> page = new Page<>(pageNum, limit);
        Map<String, Object> map = eduScholarService.getPageScholarList(page, filterVo);
        return Result.success().data(map);
    }

    @GetMapping("{scholarId}")
    public Result getAllByScholarId(@PathVariable String scholarId){
        QueryWrapper<EduScholarCitation> citationWrapper = new QueryWrapper<>();
        citationWrapper.eq("scholar_Id", scholarId).select("year","citations");
        List<EduScholarCitation> citationList = citationService.list(citationWrapper);

        QueryWrapper<EduMemberScholar> memberWrapper = new QueryWrapper<>();
        memberWrapper.eq("scholar_Id", scholarId).select("id","name");
        List<EduMemberScholar> memberList = memberScholarService.list(memberWrapper);
        List<Long> memberIdList = memberList.stream().map(EduMemberScholar::getId).toList();
        List<EduMember> eduMembers = memberService.listByIds(memberIdList);
        EduScholar scholar = eduScholarService.getById(scholarId);
        return Result.success().data("citation",citationList).data("member", eduMembers).data("scholar",scholar);
    }

    /**
     * return citation static by memberId
     * @param memberId the ID of member
     * @return Map<String, IntSummaryStatics>
     */
    @GetMapping("citation/{memberId}")
    public Result getCitationByMemberId(@PathVariable Long memberId){
        List<EduScholarCitation> list = citationService.list(new QueryWrapper<EduScholarCitation>().eq("id", memberId));
        Map<String, IntSummaryStatistics> map = list.parallelStream()
                .collect(Collectors.groupingBy(EduScholarCitation::getYear,
                Collectors.summarizingInt(EduScholarCitation::getCitations)));
        return Result.success().data(map);

    }

    @GetMapping("{memberId}/{pageNum}/{limit}")
    public Result getPageScholarByMemberId(@PathVariable Long memberId,
                                           @PathVariable Integer pageNum, @PathVariable Integer limit){
        Page<EduScholar> page = new Page<>(pageNum, limit);
        QueryWrapper<EduMemberScholar> wrapper = new QueryWrapper<>();
        wrapper.eq("id" , memberId);
        List<EduMemberScholar> memberScholarList = memberScholarService.list(wrapper);
        List<String> scholarIdList = memberScholarList.stream().map(EduMemberScholar::getScholarId).collect(Collectors.toList());

        QueryWrapper<EduScholar> scholarWrapper = new QueryWrapper<>();
        scholarWrapper.in("id",scholarIdList);
//        Map<String, Object> map = eduScholarService.getPageScholarByMemberId(memberId, pageNum, limit);
        eduScholarService.page(page, scholarWrapper);
        Map<String, Object> map = PageUtil.toMap(page);
        return Result.success().data(map);
    }

}
