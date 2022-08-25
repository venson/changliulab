package com.venson;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptVerify {
    @Test
    public void encodeTest(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("111111"));
        System.out.println(encoder.matches("111111","$2a$10$S7ZwODxXQRyOO0HrqVWHMeQ35fbLHz/w8XG4Wy5U3KQKhvtiwv/5e"));
        System.out.println(encoder.encode("111112"));
        System.out.println(encoder.encode("0337f5fd-8059-4020-abf3-91ffecb1a94c"));

    }
}
