package com.venson.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduMemberScholar;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.EduScholarCitation;
import com.venson.eduservice.entity.frontvo.ScholarFrontFilterVo;
import com.venson.eduservice.service.EduMemberScholarService;
import com.venson.eduservice.service.EduScholarCitationService;
import com.venson.eduservice.service.EduScholarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("eduservice/scholar")
public class ScholarFrontController {

    @Autowired
    private EduScholarService eduScholarService;
    @Autowired
    private EduScholarCitationService citationService;
    @Autowired
    private EduMemberScholarService memberService;
    @PostMapping("{pageNum}/{limit}")
    public RMessage getPageScholar(@PathVariable Integer pageNum, @PathVariable Integer limit,
                                       @RequestBody ScholarFrontFilterVo filterVo){

        Page<EduScholar> page = new Page<>(pageNum, limit);
        Map<String, Object> map = eduScholarService.getPageScholarList(page, filterVo);
        return RMessage.ok().data(map);
    }

    @GetMapping("{scholarId}")
    public RMessage getAllByScholarId(@PathVariable String scholarId){
        QueryWrapper<EduScholarCitation> citationWrapper = new QueryWrapper<>();
        citationWrapper.eq("scholar_Id", scholarId).select("year","citations");
        List<EduScholarCitation> citationList = citationService.list(citationWrapper);

        QueryWrapper<EduMemberScholar> memberWrapper = new QueryWrapper<>();
        memberWrapper.eq("scholar_Id", scholarId).select("id","name");
        List<EduMemberScholar> memberList =memberService.list(memberWrapper);
        EduScholar scholar = eduScholarService.getById(scholarId);
        return RMessage.ok().data("citation",citationList).data("member", memberList).data("scholar",scholar);
    }

    @GetMapping("citation/{memberId}")
    public RMessage getCitationByMemberId(@PathVariable String memberId){
        List<EduScholarCitation> list = citationService.list(new QueryWrapper<EduScholarCitation>().eq("id", memberId));
        Map<String, IntSummaryStatistics> map = list.parallelStream().collect(Collectors.groupingBy(EduScholarCitation::getYear,
                Collectors.summarizingInt(EduScholarCitation::getCitations)));
        return RMessage.ok().data(map);

    }

    @GetMapping("{memberId}/{pageNum}/{limit}")
    public RMessage getPageScholarByMemberId(@PathVariable String memberId,
                         @PathVariable Integer pageNum,@PathVariable Integer limit){
        Page<EduScholar> page = new Page<>(pageNum, limit);
        QueryWrapper<EduMemberScholar> wrapper = new QueryWrapper<>();
        wrapper.eq("id" , memberId);
        List<EduMemberScholar> memberScholarList = memberService.list(wrapper);
        List<String> scholarIdList = memberScholarList.stream().map(EduMemberScholar::getScholarId).collect(Collectors.toList());

        QueryWrapper<EduScholar> scholarWrapper = new QueryWrapper<>();
        scholarWrapper.in("id",scholarIdList);
//        Map<String, Object> map = eduScholarService.getPageScholarByMemberId(memberId, pageNum, limit);
        eduScholarService.page(page, scholarWrapper);
        Map<String, Object> map = PageUtil.toMap(page);
        return RMessage.ok().data(map);
    }

}
