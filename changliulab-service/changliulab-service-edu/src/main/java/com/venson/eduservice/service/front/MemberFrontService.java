package com.venson.eduservice.service.front;

import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.enums.MemberLevel;
import com.venson.eduservice.entity.front.dto.MemberFrontBriefDTO;

import java.util.List;

public interface MemberFrontService {
    List<EduMember> getPIMemberFront();

    PageResponse<EduMember> getPageFrontMemberListByLevel(Integer page, Integer limit, MemberLevel level);

    EduMember getMemberFrontByID(Long id);

    List<MemberFrontBriefDTO> getFrontIndexMember();
}
