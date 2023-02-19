package com.venson.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.aclservice.entity.FrontUser;
import com.venson.aclservice.entity.vo.FrontUserResetPasswordVo;
import com.venson.aclservice.entity.vo.RegistrationVo;
import com.venson.aclservice.entity.vo.front.FrontUserVo;


/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author venson
 * @since 2022-05-24
 */
public interface FrontUserService extends IService<FrontUser> {


    void register(RegistrationVo vo);

//    List<FrontUser> getMemberList(String filter);

    FrontUser selectByUsername(String username);


    Boolean resetPassword(FrontUserResetPasswordVo vo);

    FrontUserVo getUserById(Long userId);
}
