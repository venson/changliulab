package com.venson.eduservice.service;

import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.dto.MemberQuery;
import com.venson.eduservice.entity.front.dto.MemberFrontBriefDTO;
import com.venson.eduservice.entity.vo.MemberVo;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-02
 */
public interface EduMemberService extends IService<EduMember> {

    PageResponse<EduMember> getMemberPage(Integer pageNum, Integer limit, MemberQuery memberQuery);

    void updateMember(Long id, MemberVo member);

    List<MemberFrontBriefDTO> getFrontIndexMember();

    List<EduMember> getCurrentMember();

    List<EduMember> getAllMember();
}
