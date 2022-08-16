package com.venson.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {
    public static final long EXPIRE= 1000* 60 * 60 *24;
    public static final String APP_SECRET = "sdfsdfwierjwfndjvaiwejrsdfherh";

    public static String getJwtToken(Long id, String nickName){
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .setSubject("ChangLiuLab")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id",id)
                .claim("nickName",nickName)
                .signWith(SignatureAlgorithm.HS256,APP_SECRET)
                .compact();
    }

    public static boolean checkToken(String jwtToken){
        if(ObjectUtils.isEmpty(jwtToken)){
            return false;
        }
        try{
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean checkToken(HttpServletRequest request){
        try {
            String token = request.getHeader("token");
            if (ObjectUtils.isEmpty(token)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static Long getMemberIdByToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(ObjectUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (Long)claims.get("id");
    }
}
