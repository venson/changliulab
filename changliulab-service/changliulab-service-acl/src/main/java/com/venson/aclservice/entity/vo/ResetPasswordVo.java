package com.venson.aclservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPasswordVo implements Serializable {
    private static final long serialVersionUID = 123123L;
    private String eMail;
    private String randomPassword;
}
