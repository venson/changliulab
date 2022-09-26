package com.venson.aclservice.entity.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ResetPasswordVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 123123L;
    private String email;
    private String randomPassword;
}
