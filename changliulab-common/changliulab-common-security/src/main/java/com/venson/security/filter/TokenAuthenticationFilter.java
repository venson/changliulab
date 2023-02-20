package com.venson.security.filter;

import com.venson.commonutils.ResponseUtil;
import com.venson.commonutils.Result;
import com.venson.security.adapter.AuthPathAdapter;
import com.venson.security.entity.AuthContext;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.security.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 访问过滤器
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Slf4j
@Deprecated
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private final TokenManager tokenManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthPathAdapter pathAdapter;

    public TokenAuthenticationFilter(AuthenticationManager authManager,
                                     TokenManager tokenManager,
                                     RedisTemplate<String,Object> redisTemplate,
                                     AuthPathAdapter pathAdapter) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.pathAdapter = pathAdapter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> pathWhiteList= pathAdapter.pathWhiteList();
        String requestURI = req.getRequestURI();

        if(!ObjectUtils.isEmpty(pathWhiteList)) {
            for (String pathPattern : pathWhiteList) {
                if(antPathMatcher.match(pathPattern,requestURI)){
                    chain.doFilter(req, res);
                    return;
                }
            }
        }

        UsernamePasswordAuthenticationToken authentication ;
        UserContextInfoBO userContextInfoBO;
        try {
            userContextInfoBO = getRedisUserByRequest(req);
            authentication = getAuthentication(req);
        } catch (Exception e) {
            ResponseUtil.out(res, Result.illegalToken());
            log.info("auth Failed:");
            log.info(req.getRemoteAddr());
            return;

        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthContext.set(userContextInfoBO);
        } else {
            ResponseUtil.out(res, Result.tokenExpire());
            return;
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        UserContextInfoBO userContextInfoBO = getRedisUserByRequest(request);
        if (userContextInfoBO != null) {

            List<String> permissionValueList = userContextInfoBO.getPermissionValueList();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for(String permissionValue : permissionValueList) {
                if(ObjectUtils.isEmpty(permissionValue)) continue;
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }

            if (!ObjectUtils.isEmpty(userContextInfoBO.getUsername())) {
                return new UsernamePasswordAuthenticationToken(userContextInfoBO.getUsername(),userContextInfoBO.getToken(), authorities);
            }
            return null;
        }
        return null;
    }

    private UserContextInfoBO getRedisUserByRequest(HttpServletRequest request){
        // token置于header里
        String token = request.getHeader("X-Token").trim();
        if (StringUtils.hasText(token)) {
            String redisKey = tokenManager.getRedisKeyFromToken(token);
            return (UserContextInfoBO) redisTemplate.opsForValue().get(redisKey);
        }
        return null;
    }
}
