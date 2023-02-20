package com.venson.security.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String nickName;

	private String token;

	private String email;

}



