package com.venson.security.config;

import com.venson.security.adapter.AuthPathAdapter;
import com.venson.security.filter.TokenAuthenticationFilter;
import com.venson.security.filter.TokenLoginFilter;
import com.venson.security.security.TokenLogoutHandler;
import com.venson.security.security.TokenManager;
import com.venson.security.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * <p>
 * Security配置类
 * </p>
 *
 * @author qy
 * @since 2019-11-18
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
    @Deprecated
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  UserDetailsService userDetailsService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AuthPathAdapter pathAdapter;

    /**
     * 配置设置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/admin/acl/login").permitAll();
        http.exceptionHandling()
            .authenticationEntryPoint(new UnauthorizedEntryPoint());
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.csrf().disable()
            .authorizeRequests().and().logout().logoutUrl("/admin/acl/index/logout")
            .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
            .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
//            .addFilter(new TokenAuthenticationFilter(authenticationManager(),
//                    tokenManager, redisTemplate, pathAdapter))
                    .httpBasic();
    }

    /**
     * 密码处理
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
