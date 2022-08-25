package com.venson.security.entity.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * used to composite in SecurityUser
 */
@Data
public class UserInfoBO implements Serializable {

    private String username;

    private Long id;

    private String eMail;

    private String password;
}
