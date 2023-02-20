package com.venson.eduservice.service.front;

import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.front.dto.ScholarFrontDTO;
import com.venson.eduservice.entity.front.vo.CitationFrontVo;
import com.venson.eduservice.entity.front.vo.ScholarFrontFilterVo;

public interface ScholarFrontService {
    PageResponse<EduScholar> getPageScholarByMemberId(Long id, Integer page, Integer limit);

    ScholarFrontDTO getAllByScholarId(String scholarId);

    PageResponse<EduScholar> getPageScholarWithFilter(Integer pageNum, Integer limit, ScholarFrontFilterVo filterVo);
    PageResponse<EduScholar> doGetPageScholar(Integer pageNum, Integer limit);

    CitationFrontVo getCitationByMemberId(Long memberId);
}
