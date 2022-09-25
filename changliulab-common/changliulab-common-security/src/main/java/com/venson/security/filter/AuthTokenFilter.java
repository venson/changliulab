package com.venson.security.filter;

import com.venson.commonutils.ResponseUtil;
import com.venson.commonutils.Result;
import com.venson.security.adapter.AuthPathAdapter;
import com.venson.security.entity.AuthContext;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.security.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter implements Ordered {
    private final TokenManager tokenManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthPathAdapter pathAdapter;

    public AuthTokenFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate, AuthPathAdapter pathAdapter) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.pathAdapter = pathAdapter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, @NotNull HttpServletResponse res, @NotNull FilterChain chain) throws ServletException, IOException {

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String requestURI = req.getRequestURI();
        List<String> loginPathList= pathAdapter.loginPathList();
        List<String> pathWhiteList= pathAdapter.pathWhiteList();

        if(!ObjectUtils.isEmpty(loginPathList)) {
            for (String pathPattern :loginPathList) {
                if(antPathMatcher.match(pathPattern,requestURI)){
                    chain.doFilter(req, res);
                    return;
                }
            }
        }
        String token = getToken(req);
        // request without token, can access to pathWhitList
        if(token==null || "undefined".equals(token)){
            for (String pathPattern :pathWhiteList) {
                if(antPathMatcher.match(pathPattern,requestURI)){
                    chain.doFilter(req, res);
                    return;
                }
            }
        }
        // if got token, check auth
        UsernamePasswordAuthenticationToken authentication ;
        try {
            authentication = getAuthentication(token);
        } catch (Exception e) {
            ResponseUtil.out(res, Result.illegalToken());
            log.info("auth Failed:");
            log.info(req.getRemoteAddr());
            log.info(req.getRequestURI());
            return;

        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseUtil.out(res, Result.tokenExpire());
            return;
        }
        chain.doFilter(req, res);

    }
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        // token置于header里
        UserContextInfoBO userContextInfoBO = getRedisUserByRequest(token);
        if (userContextInfoBO != null) {

            List<String> permissionValueList = userContextInfoBO.getPermissionValueList();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for(String permissionValue : permissionValueList) {
                if(ObjectUtils.isEmpty(permissionValue)) continue;
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }

            return new UsernamePasswordAuthenticationToken(userContextInfoBO,userContextInfoBO.getToken(), authorities);
        }
        return null;
    }
    private String getToken(HttpServletRequest request){
        String token = request.getHeader("X-Token");
        if (StringUtils.hasText(token)) {
            return token.trim();
        }
        return null;

    }

    private UserContextInfoBO getRedisUserByRequest(String token){
        // token置于header里
        if (StringUtils.hasText(token)) {
            String redisKey = tokenManager.getRedisKeyFromToken(token);
            Object o = redisTemplate.opsForValue().get(redisKey);
            if( o instanceof UserContextInfoBO){
                return (UserContextInfoBO)o;
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
