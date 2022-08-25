package com.venson.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.user.entity.UserCenterMember;
import com.venson.user.entity.vo.RegistrationVo;

import java.util.List;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author venson
 * @since 2022-05-24
 */
public interface UserCenterService extends IService<UserCenterMember> {

    String login(UserCenterMember userCenterMember);

    String register(RegistrationVo vo);

    List<UserCenterMember> getMemberList(String filter);
}
