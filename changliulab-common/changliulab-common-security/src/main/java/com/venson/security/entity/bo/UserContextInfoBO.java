package com.venson.security.entity.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Bo will be store in AuthContext(ThreadLocal)
 */
@Data
public class UserContextInfoBO implements Serializable {

    private Long id;

    private String username;

    private String eMail;

    private String token;

    private List<String> permissionValueList;


}
