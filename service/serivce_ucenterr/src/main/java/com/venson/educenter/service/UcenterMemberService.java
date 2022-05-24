package com.venson.educenter.service;

import com.venson.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author venson
 * @since 2022-05-24
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    String register(UcenterMember ucenterMember);
}
