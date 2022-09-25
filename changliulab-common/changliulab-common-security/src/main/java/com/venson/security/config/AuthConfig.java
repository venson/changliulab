package com.venson.security.config;

import com.venson.security.adapter.AuthPathAdapter;
import com.venson.security.filter.AuthTokenFilter;
import com.venson.security.filter.TokenAuthenticationFilter;
import com.venson.security.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


/**
 * test config to use new feature of spring security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private AuthPathAdapter pathAdapter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/auth/admin/login").anonymous();
        http.authorizeRequests().antMatchers("/auth/user/login").anonymous();
        http.authorizeRequests().antMatchers("/*/front/**").permitAll();

        http.csrf().disable().httpBasic().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new AuthTokenFilter(tokenManager,redisTemplate,pathAdapter), BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();
        return http.build();

    }
}
