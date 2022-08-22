package com.venson.user.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordNotFound {
    public static final String instance = new BCryptPasswordEncoder().encode("user password not found");

    private BCryptPasswordNotFound() {}
}
