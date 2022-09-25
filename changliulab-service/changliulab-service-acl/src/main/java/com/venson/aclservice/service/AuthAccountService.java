package com.venson.aclservice.service;


import com.venson.security.entity.SecurityUser;

public interface AuthAccountService {
    SecurityUser getUserByUsername(String username);
}
