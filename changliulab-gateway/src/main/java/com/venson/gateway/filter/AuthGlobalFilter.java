package com.venson.gateway.filter;

import com.google.gson.JsonObject;
import com.venson.commonutils.JwtUtils;
import com.venson.commonutils.ResultCode;
import com.venson.commonutils.constant.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {


    @Value("${jwt.token.key}")
    private String tokenSignKey;
    private final PathPattern pattern = PathPatternParser.defaultInstance.parse(AuthConstants.ADMIN_PATTERN);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        ServerHttpResponse response = exchange.getResponse();

        String token = getToken(request);
        if (!AuthConstants.LOGIN_PATH_ADMIN.equals(path)
                && pattern.matches(PathContainer.parsePath(path))) {
            try {
                JwtUtils.setJwtKey(tokenSignKey);
                String tokenSubject = JwtUtils.getTokenSubject(token);
                if (!StringUtils.hasText(tokenSubject) && !tokenSubject.contains(AuthConstants.ADMIN_PREFIX)) {
                    return tokenExpired(response);
                }
            } catch (Exception e) {
                log.error("Invalid token:[" +path+"]ip:["+request.getRemoteAddress() +"]");
                log.info(request.getHeaders().toString());
                return tokenExpired(response);
            }
        }



        return chain.filter(exchange);
    }

    private String getToken(ServerHttpRequest request) {
        List<String> tokenList = request.getHeaders().get(AuthConstants.TOKEN_KEY);
        if (tokenList == null) {
            return null;
        }
        String token = tokenList.get(0);
        if (token.contains(AuthConstants.BEARER)) {
            return token.split(" ")[1];
        }
        return null;
    }


    private Mono<Void> tokenExpired(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", ResultCode.TOKEN_EXPIRE.getValue() );
        message.addProperty("data", ResultCode.TOKEN_EXPIRE.getDesc());
        byte[] bytes = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(buffer));
    }

//    @Deprecated
//    private Mono<Void> tokenIllegal(ServerHttpResponse response) {
//        JsonObject message = new JsonObject();
//        message.addProperty("success", false);
//        message.addProperty("code", ResultCode.ILLEGAL_TOKEN.getValue());
//        message.addProperty("data", ResultCode.ILLEGAL_TOKEN.getDesc());
//        byte[] bytes = message.toString().getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = response.bufferFactory().wrap(bytes);
//        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//        return response.writeWith(Mono.just(buffer));
//    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
