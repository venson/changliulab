package com.venson.security.filter;

import com.venson.commonutils.RMessage;
import com.venson.commonutils.ResponseUtil;
import com.venson.security.entity.SecurityConstants;
import com.venson.security.entity.SecurityUser;
import com.venson.security.entity.User;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.entity.bo.UserInfoBO;
import com.venson.security.security.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final RedisTemplate<String,Object> redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager,
                            TokenManager tokenManager,
                            RedisTemplate<String,Object> redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),
                            user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth)  {
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        UserInfoBO tokenInfoBO = ((SecurityUser) auth.getPrincipal()).getUser();
        UserContextInfoBO userContextInfoBO = new UserContextInfoBO();
        BeanUtils.copyProperties(tokenInfoBO,userContextInfoBO);
        userContextInfoBO.setPermissionValueList(user.getPermissionValueList());
        String redisKey = SecurityConstants.ADMIN_PREFIX +tokenInfoBO.getId();
        String token = tokenManager.createToken(redisKey);
        userContextInfoBO.setToken(token);
        redisTemplate.opsForValue().set(redisKey, userContextInfoBO,
                SecurityConstants.EXPIRE_24H_S, TimeUnit.SECONDS);

        ResponseUtil.out(res, RMessage.ok().data("token", token));
    }

    /**
     * 登录失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) {
        ResponseUtil.out(response, RMessage.error());
    }
}
