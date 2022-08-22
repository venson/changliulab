package com.venson.security.filter;

import com.venson.commonutils.ResponseUtil;
import com.venson.security.entity.AuthContext;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.security.TokenManager;
import com.venson.commonutils.RMessage;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
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
@WebFilter
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private final TokenManager tokenManager;
    private final RedisTemplate<String, Object> redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager,
                                     TokenManager tokenManager,
                                     RedisTemplate<String,Object> redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.info("================="+req.getRequestURI());
        if(!req.getRequestURI().contains("admin")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;
        UserContextInfoBO userContextInfoBO =null;
        try {
            userContextInfoBO = getRedisUserByRequest(req);
            authentication = getAuthentication(req);
        } catch (Exception e) {
            ResponseUtil.out(res, RMessage.error());
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthContext.set(userContextInfoBO);
        } else {
            ResponseUtil.out(res, RMessage.error());
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
        String token = request.getHeader("X-Token");
        if (token != null && !"".equals(token.trim())) {
            String redisKey = tokenManager.getUserFromToken(token);
            return (UserContextInfoBO) redisTemplate.opsForValue().get(redisKey);
        }
        return null;
    }
}
