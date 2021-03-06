package com.venson.gateway.filter;

import com.google.gson.JsonObject;
import com.venson.commonutils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if(antPathMatcher.match("/api/**/auth/**",path)){
            List<String> tokenList = request.getHeaders().get("X-Token");
            if(tokenList ==null){
                ServerHttpResponse response = exchange.getResponse();
                return out(response);
            }else{
                boolean checkToken = JwtUtils.checkToken(tokenList.get(0));
                if(!checkToken){
                    ServerHttpResponse response = exchange.getResponse();
                    return out(response);
                }
            }
        }

        if(antPathMatcher.match("/**/inner/**", path)){
            ServerHttpResponse response = exchange.getResponse();
            return out(response);
        }
        return chain.filter(exchange);
    }
    private Mono<Void> out(ServerHttpResponse response){
        JsonObject message = new JsonObject();
        message.addProperty("success",false);
        message.addProperty("code", 28004);
        message.addProperty("data","auth failed");
        byte[] bytes = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().add("Content-Type", "application/json:charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
