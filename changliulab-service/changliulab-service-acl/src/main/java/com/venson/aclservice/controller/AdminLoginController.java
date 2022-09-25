package com.venson.aclservice.controller;

import com.venson.aclservice.entity.dto.UserInfoDTO;
import com.venson.aclservice.entity.vo.UserLogin;
import com.venson.aclservice.service.AuthAccountService;
import com.venson.commonutils.ResponseUtil;
import com.venson.commonutils.Result;
import com.venson.security.config.PasswordNotFoundBCryptEncoded;
import com.venson.security.entity.SecurityUser;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.entity.bo.UserInfoBO;
import com.venson.security.entity.constant.AuthConstants;
import com.venson.security.security.TokenManager;
import com.venson.security.utils.ContextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * use controller instead of webMvcconfigadaptor
 */
@RestController()
@RequestMapping("/auth/admin/login")
public class AdminLoginController {

    @Autowired
    private UserDetailsService adminUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @PostMapping()
    public Result login(@RequestBody UserLogin user){
        SecurityUser securityUser = (SecurityUser) adminUserDetailsService.loadUserByUsername(user.getUsername());
        if(securityUser==null){
            passwordEncoder.matches("user not found", PasswordNotFoundBCryptEncoded.instance);

        }else{
            boolean matches = passwordEncoder.matches(user.getPassword(),securityUser.getPassword());
            if(matches){

                UserInfoBO tokenInfoBO =securityUser.getCurrentUserInfo();
                UserContextInfoBO userContextInfoBO = new UserContextInfoBO();
                BeanUtils.copyProperties(tokenInfoBO,userContextInfoBO);
                userContextInfoBO.setPermissionValueList(securityUser.getPermissionValueList());
                String redisKey = String.join(":", AuthConstants.ADMIN_PREFIX ,tokenInfoBO.getId().toString());
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
