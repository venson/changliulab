package com.venson.aclservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLogin implements Serializable {
    private String username;
    private String password;
}
