package com.venson.test;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

public class JwtKeyGen {
    @Test
    public void keyGen(){
        byte[] encoded = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String encode = Encoders.BASE64.encode(encoded);
        System.out.println(encode);
    }

}
