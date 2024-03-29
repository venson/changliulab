package com.venson.security.config;

import com.venson.security.adapter.AuthPathAdapter;
import com.venson.security.adapter.DefaultAuthPathAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;

@Configuration
public class SecurityBeanConfig {
    @Bean
    public AntPathMatcher antPathMatcher(){ return new AntPathMatcher();}
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthPathAdapter authPathAdapter(){
        return new DefaultAuthPathAdapter();
    }
}
