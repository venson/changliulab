package com.venson.eduservice.mapper;

import com.venson.eduservice.entity.EduMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.venson.eduservice.entity.front.dto.MemberFrontBriefDTO;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-05-02
 */
public interface EduMemberMapper extends BaseMapper<EduMember> {

    List<MemberFrontBriefDTO> getFrontIndexMember();
}
