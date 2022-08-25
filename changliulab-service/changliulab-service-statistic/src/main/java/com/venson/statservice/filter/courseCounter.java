package com.venson.statservice.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class courseCounter implements Filter {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        redisTemplate.opsForValue().increment("site:viewCount",1L);



        chain.doFilter(request,response);
    }
}
