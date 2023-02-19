package com.venson.security.config;

import com.venson.security.adapter.AuthPathAdapter;
import com.venson.security.filter.AuthTokenFilter;
import com.venson.security.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
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
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AuthPathAdapter pathAdapter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/auth/admin/login").anonymous();
        http.authorizeRequests().antMatchers("/auth/front/login").anonymous();
        http.authorizeRequests().antMatchers("/*/front/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, // Swagger的资源路径需要允许访问
                        "/",
                        "/swagger-ui.html",
                        "/swagger-ui/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v3/api-docs/**"
                )
                .permitAll();
        http.csrf().disable().httpBasic().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new AuthTokenFilter(tokenManager, redisTemplate, pathAdapter), BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();
        return http.build();

    }
}
