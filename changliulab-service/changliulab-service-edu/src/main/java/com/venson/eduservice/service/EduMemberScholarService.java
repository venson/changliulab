package com.venson.eduservice.service;

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

    void updateMemberScholar(String scholarId, List<EduMemberScholar> memberList);

    List<EduMemberScholar> getCurrentMemberByScholarId(String scholarId);

    void saveMemberScholar(String scholarId, List<EduMemberScholar> memberList);
}
