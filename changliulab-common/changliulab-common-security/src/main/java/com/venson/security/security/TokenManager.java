package com.venson.security.security;

import io. jsonwebtoken.CompressionCodecs;
import io. jsonwebtoken.Jwts;
import io. jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Component
@Slf4j
public class TokenManager {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${jwt.token.key}")
    private String tokenSign;
    private static Key securityKey;

    public String createToken(String jwtKey) {
        if(securityKey==null){
            byte[] decode = Decoders.BASE64.decode(tokenSign);
            securityKey = Keys.hmacShaKeyFor(decode);
        }
        long tokenExpiration = 24 * 60 * 60 * 1000;
        return Jwts.builder().setSubject(jwtKey)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(securityKey,SignatureAlgorithm.HS512).compressWith(CompressionCodecs.GZIP).compact();
    }

    public String getRedisKeyFromToken(String token) {
        if(securityKey==null){
            byte[] decode = Decoders.BASE64.decode(tokenSign);
            securityKey = Keys.hmacShaKeyFor(decode);
        }
        return Jwts.parserBuilder().setSigningKey(securityKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public void removeToken(String token) {
        String redisKey = getRedisKeyFromToken(token);
        redisTemplate.opsForValue().getAndDelete(redisKey);
    }

}
