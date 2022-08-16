package com.venson.educenter.entity.vo;

import lombok.Data;

@Data
public class RegistrationVo {
    private String nickName;
    private String email;
    private String password;
    private String securityCode;
    private String avatar;
}
