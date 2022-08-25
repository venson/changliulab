package com.venson.msmservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.commonutils.RandomString;
import com.venson.msmservice.entity.vo.ResetPasswordVo;
import com.venson.msmservice.service.MsmService;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
//@CrossOrigin
@RequestMapping("/edumsm/admin/msm")
@Slf4j
public class MsmController {

    private final MsmService msmService;
    private final RedisTemplate<String,String> redisTemplate;

    public MsmController(MsmService msmService ,RedisTemplate<String,String> redisTemplate) {
        this.msmService = msmService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping(value = "sendEmail")
    public RMessage sendEmail(@RequestBody String emailUrl){
        log.info(emailUrl = emailUrl.replace("\"",""));
        Long expireTime = redisTemplate.getExpire(emailUrl, TimeUnit.MINUTES);
        if(expireTime>= 19){
            throw new CustomizedException(200001, "interval is too short");
        }
        String code = RandomString.randomCode();
        boolean result = msmService.sendCode(emailUrl, code, "Registration Security Code");
        if (result){
            redisTemplate.opsForValue().set(emailUrl, code, 20, TimeUnit.MINUTES);
            return RMessage.ok();
        }
        return RMessage.error();
    }
    @PostMapping(value = "resetEmail")
    public RMessage resetEmail(@RequestBody ResetPasswordVo passwordVo){
        boolean result = msmService.sendCode(passwordVo.getEMail(),passwordVo.getRandomPassword(), "Rest Random Password");
        return RMessage.error();
    }
}
