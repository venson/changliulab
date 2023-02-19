package com.venson.security.filter;

import com.venson.commonutils.Result;
import com.venson.commonutils.ResponseUtil;
import com.venson.security.entity.AdminUser;
import com.venson.commonutils.constant.AuthConstants;
import com.venson.security.entity.SecurityUser;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.entity.bo.UserInfoBO;
import com.venson.security.security.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venson.servicebase.entity.TokenBo;
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
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Deprecated
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
        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/auth/admin/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            AdminUser adminUser = new ObjectMapper().readValue(req.getInputStream(), AdminUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(adminUser.getUsername(),
                            adminUser.getPassword(), new ArrayList<>()));
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
        String redisKey = String.join(":", AuthConstants.ADMIN_PREFIX ,tokenInfoBO.getId().toString());
        String token = tokenManager.createToken(redisKey);
        userContextInfoBO.setToken(token);
        // store UserContextInfoBO to redis
        redisTemplate.opsForValue().set(redisKey, userContextInfoBO,
                AuthConstants.EXPIRE_24H_S, TimeUnit.SECONDS);
        ResponseUtil.out(res, Result.success(new TokenBo(token)));
    }

    /**
     * 登录失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) {
        ResponseUtil.out(response, Result.error("unsuccessful auth"));
    }
}
