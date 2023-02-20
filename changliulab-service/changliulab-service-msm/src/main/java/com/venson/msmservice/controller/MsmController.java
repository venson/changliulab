package com.venson.msmservice.controller;

import com.venson.commonutils.Result;
import com.venson.commonutils.RandomString;
import com.venson.msmservice.entity.vo.ResetPasswordVo;
import com.venson.msmservice.service.MsmService;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.utils.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/admin/msm")
@Slf4j
public class MsmController {

    private final MsmService msmService;
    private final StringRedisTemplate redisTemplate;

    public MsmController(MsmService msmService ,StringRedisTemplate redisTemplate) {
        this.msmService = msmService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping(value = "securityCode")
    public Result<String> sendEmail(@RequestBody String emailUrl){
        log.info("Security Code to Email:" + emailUrl);
        Long expireTime = redisTemplate.getExpire(emailUrl, TimeUnit.MINUTES);
        if(expireTime!=null && expireTime>= 1){
            return Result.error("Please Wait");
        }
        String code = RandomString.randomCode();
        boolean result = msmService.sendCode(emailUrl, code,"Security Code",
                "Registration Security Code",20);
        if (result){
            redisTemplate.opsForValue().set(emailUrl, code, 20, TimeUnit.MINUTES);
            return Result.success();
        }
        return Result.error();
    }
    @PostMapping(value = "resetPassword")
    public Result<String> resetEmail(@RequestBody ResetPasswordVo passwordVo){
        UserContextInfoBO userContext = ContextUtils.getUserContext();
        Assert.notNull(userContext,"Invalid user");
        Assert.isTrue(userContext.getEmail().equals(passwordVo.getEmail()),"Email not match");
        log.info("reset Password to Email:" + passwordVo.getEmail());
        msmService.sendCode(passwordVo.getEmail(),
                passwordVo.getRandomPassword(),"New Password", "Reset Password",0);
        return Result.success();
    }
}
