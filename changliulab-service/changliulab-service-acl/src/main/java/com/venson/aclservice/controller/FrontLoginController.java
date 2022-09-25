package com.venson.aclservice.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.venson.aclservice.entity.FrontUser;
import com.venson.aclservice.entity.vo.RegistrationVo;
import com.venson.aclservice.entity.vo.UserLogin;
import com.venson.aclservice.service.FrontUserService;
import com.venson.commonutils.Result;
import com.venson.security.config.PasswordNotFoundBCryptEncoded;
import com.venson.security.entity.AuthContext;
import com.venson.security.entity.SecurityUser;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.entity.bo.UserInfoBO;
import com.venson.security.entity.constant.AuthConstants;
import com.venson.security.security.TokenManager;
import com.venson.security.utils.ContextUtils;
import com.venson.servicebase.exception.CustomizedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/auth/front")
public class FrontLoginController {
    @Autowired
    private FrontUserService frontUserService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private UserDetailsService frontUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public Result login(@RequestBody UserLogin userLogin){
        String username= userLogin.getUsername().trim().replace("\"","");
        String password = userLogin.getPassword();
        if(!StringUtils.hasText(username) || !StringUtils.hasText(password)){
            throw new CustomizedException(20001, "invalid email or password");
        }
        SecurityUser securityUser= (SecurityUser)frontUserDetailsService.loadUserByUsername(username);
        if(securityUser == null) {
            passwordEncoder.matches("password not found", PasswordNotFoundBCryptEncoded.instance);
        }else{
            if(passwordEncoder.matches(password, securityUser.getPassword())){

                UserInfoBO tokenInfoBO =securityUser.getCurrentUserInfo();
                UserContextInfoBO userContextInfoBO = new UserContextInfoBO();
                BeanUtils.copyProperties(tokenInfoBO,userContextInfoBO);
                userContextInfoBO.setPermissionValueList(securityUser.getPermissionValueList());
                String redisKey = String.join(":", AuthConstants.USER_PREFIX ,tokenInfoBO.getId().toString());
                String token = tokenManager.createToken(redisKey);
                userContextInfoBO.setToken(token);
                // store UserContextInfoBO to redis
                redisTemplate.opsForValue().set(redisKey, userContextInfoBO,
                        AuthConstants.EXPIRE_24H_S, TimeUnit.SECONDS);
                return Result.success().data("token",token);
            }
        }
        return Result.unAuthorized();

    }

    @PostMapping("logout")
    public Result logout(){
        UserContextInfoBO userContext = ContextUtils.getUserContext();
        if(userContext!=null){
            tokenManager.removeToken(userContext.getToken());
            return Result.success();
        }
        return Result.error();
    }

}
