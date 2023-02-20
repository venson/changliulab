package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.EduMemberScholar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-06-20
 */
public interface EduMemberScholarService extends IService<EduMemberScholar> {

    void updateMemberScholar(Long scholarId, List<EduMember> memberList);

    List<EduMemberScholar> getMembersByScholarId(Long scholarId);

//    void saveMemberScholar(Long scholarId, List<EduMemberScholar> memberList);

    List<Long> getScholarIdListByMemberId(Long memberId);

    List<Long> getMemberIdsByScholarId(Long id);

    void updateMemberScholarByMemberIdList(Long scholarId, List<Long> memberIdList, List<EduMember> memberList);
}
