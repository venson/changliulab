package com.venson.security.security;

import com.venson.commonutils.constant.AuthConstants;
import com.venson.commonutils.Result;
import com.venson.commonutils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登出业务逻辑类
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenLogoutHandler implements LogoutHandler {

    private final TokenManager tokenManager;
    private final RedisTemplate<String, Object> redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String,Object> redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(AuthConstants.TOKEN_KEY);
        if (token != null) {
            tokenManager.removeToken(token);

            //清空当前用户缓存中的权限数据
            String redisKey= tokenManager.getRedisKeyFromToken(token);
            redisTemplate.delete(redisKey);
        }

        ResponseUtil.out(response, Result.success());
    }

}
