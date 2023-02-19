package com.venson.msmservice.service.impl;

import com.venson.commonutils.RandomString;
import com.venson.msmservice.entity.enums.SendStatus;
import com.venson.msmservice.service.MsmFrontService;
import com.venson.msmservice.service.MsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MsmFrontServiceImpl implements MsmFrontService {
    @Autowired
    private MsmService msmService;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public SendStatus getSecurityCode(String emailUrl) {
        emailUrl = emailUrl.trim().replace("\"", "");
        if(!StringUtils.hasText(emailUrl)){
            return SendStatus.FAILED;
        }
        int count;
        String code;
        String emailUrlCount = emailUrl + ":count";
        log.info("Security Code to Email:" + emailUrl);
        String oldCode = redisTemplate.opsForValue().get(emailUrl);
        String countString = redisTemplate.opsForValue().get(emailUrlCount);
        if(StringUtils.hasText(countString)){
            count = Integer.parseInt(countString);
            if(count <3){
                redisTemplate.opsForValue().increment(emailUrlCount);
            }else{
                return SendStatus.TOO_MANY_ATTEMPTS;
            }
        }else{
            redisTemplate.opsForValue().set(emailUrlCount,"1",24,TimeUnit.HOURS);
        }
        if(StringUtils.hasText(oldCode)){
            code =  oldCode;
        } else{
            code = RandomString.randomCode();
        }
        msmService.sendCode(emailUrl, code,"Security Code",
                "Registration Security Code",20);
        redisTemplate.opsForValue().set(emailUrl, code, 5, TimeUnit.MINUTES);
        return SendStatus.SUCCESS;
    }
}
