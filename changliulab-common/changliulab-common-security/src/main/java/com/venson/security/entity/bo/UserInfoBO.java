package com.venson.security.entity.bo;

import com.venson.security.entity.UserType;
import lombok.Data;

import java.io.Serializable;

/**
 * used to composite in SecurityUser
 */
@Data
public class UserInfoBO implements Serializable {

    private String username;

    private Long id;

    private String email;

    private String password;

    private UserType type;
}
