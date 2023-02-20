package com.venson.aclservice.entity.dto;


import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoDTO implements Serializable {
    private static final Long serialVersionUID = 234923402L;
    private Long id;
    private String username;
    private String avatar;
    private List<String> roles;
    private List<String> permissionValueList;
    private List<JSONObject> permissionList;


}
