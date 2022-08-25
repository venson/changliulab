package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.eduservice.entity.EduMember;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-02
 */
public interface EduMemberService extends IService<EduMember> {

    Map<String, Object> getMemberFrontList(Page<EduMember> teacherPage);

    Map<String, Object> getPageFrontMemberList(Integer page, Integer limit, String level);
}
