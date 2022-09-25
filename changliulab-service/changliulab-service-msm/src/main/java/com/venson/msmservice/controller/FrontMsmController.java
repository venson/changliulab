package com.venson.msmservice.controller;

import com.venson.commonutils.RandomString;
import com.venson.commonutils.Result;
import com.venson.msmservice.service.MsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/front/msm")
@Slf4j
public class FrontMsmController {
    @Autowired
    private  MsmService msmService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @PostMapping(value = "securityCode")
    public Result sendEmail(@RequestBody String emailUrl){
        emailUrl = emailUrl.trim().replace("\"", "");
        if(!StringUtils.hasText(emailUrl)){
            return Result.error().message("email is null");
        }
        int count =0;
        String emailUrlCount = emailUrl + ":count";
        log.info("Security Code to Email:" + emailUrl);
        Long expireTime = redisTemplate.getExpire(emailUrl, TimeUnit.MINUTES);
        String s = redisTemplate.opsForValue().get(emailUrlCount);
        if(s!=null){
            count = Integer.parseInt(s);
        }
        if(count>=3){
            return Result.error().message("You have sent security code for the third time, please try tomorrow");
        }
        if(expireTime!=null && expireTime>= 1){
            return Result.error().message("Please Wait");
        }
        String code = RandomString.randomCode();
        boolean result = msmService.sendCode(emailUrl, code,"Security Code",
                "Registration Security Code",20);
        if (result){
            redisTemplate.opsForValue().set(emailUrl, code, 20, TimeUnit.MINUTES);
            redisTemplate.opsForValue().increment(emailUrlCount);
            return Result.success().message("Security Code Sent, " +(2-count) + "times left for today");
        }
        return Result.error();
    }
}
