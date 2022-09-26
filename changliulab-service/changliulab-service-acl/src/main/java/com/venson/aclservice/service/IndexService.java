package com.venson.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.venson.aclservice.entity.dto.UserInfoDTO;

import java.util.List;

public interface IndexService {

    /**
     * 根据用户名获取用户登录信息
     */
    UserInfoDTO getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     */
    List<JSONObject> getMenu(String username);

}
