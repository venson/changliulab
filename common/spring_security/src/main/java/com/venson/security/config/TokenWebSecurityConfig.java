package com.venson.security.config;

import com.venson.security.filter.TokenAuthenticationFilter;
import com.venson.security.filter.TokenLoginFilter;
import com.venson.security.security.DefaultPasswordEncoder;
import com.venson.security.security.TokenLogoutHandler;
import com.venson.security.security.TokenManager;
import com.venson.security.security.UnauthorizedEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>
 * Security配置类
 * </p>
 *
 * @author qy
 * @since 2019-11-18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({TokenManager.class, UserDetailsService.class,UserDetails.class
        ,DefaultPasswordEncoder.class,RedisTemplate.class})
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final TokenManager tokenManager;
    private final DefaultPasswordEncoder defaultPasswordEncoder;
    private final RedisTemplate<String, List<String>> redisTemplate;

    public TokenWebSecurityConfig(UserDetailsService userDetailsService,
                                  DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager,
                                  RedisTemplate<String, List<String>> redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置设置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
    }

    /**
     * 密码处理
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     */
    @Override
    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/api/**",
//                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
//               );
        web.ignoring().antMatchers("/*/**"
        );
    }
}