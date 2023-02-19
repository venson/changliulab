package com.venson.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.aclservice.entity.FrontUser;
import com.venson.aclservice.entity.vo.FrontUserResetPasswordVo;
import com.venson.aclservice.entity.vo.RegistrationVo;
import com.venson.aclservice.entity.vo.front.FrontUserVo;
import com.venson.aclservice.mapper.FrontUserMapper;
import com.venson.aclservice.service.FrontUserService;
import com.venson.commonutils.RandomString;
import com.venson.commonutils.Result;
import com.venson.servicebase.entity.ResetPasswordVo;
import com.venson.servicebase.exception.CustomizedException;
import com.venson.servicebase.feign.MsmFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-05-24
 */
@Service
@Slf4j
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, FrontUser> implements FrontUserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private MsmFeign msmFeign;




    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegistrationVo vo) {
        String securityCode = vo.getSecurityCode().trim();
        String nickName = vo.getUsername().trim();
        String email = vo.getEmail().trim();
        String password = vo.getPassword();
        if(!StringUtils.hasText(email) || !StringUtils.hasText(nickName)
            || !StringUtils.hasText(password) || !StringUtils.hasText(securityCode)){
            throw new CustomizedException(20001, "invalid information");
        }

        String code = stringRedisTemplate.opsForValue().get(email);
        if(!securityCode.equals(code)){
            throw new CustomizedException(20001, "Security code error");
        }

        QueryWrapper<FrontUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Long count = baseMapper.selectCount(wrapper);
        if (count >0){
            throw new CustomizedException(200001, "duplicate email address");
        }

        FrontUser frontUser = new FrontUser();
        frontUser.setUsername(vo.getUsername());
        frontUser.setEmail(vo.getEmail());
        log.info(frontUser.toString());
        frontUser.setPassword(passwordEncoder.encode(password));
        baseMapper.insert(frontUser);
        stringRedisTemplate.delete(email);


    }

//    @Override
//    public List<FrontUser> getMemberList(String filter) {
//        LambdaQueryWrapper<FrontUser> wrapper = new LambdaQueryWrapper<>();
//        wrapper.select(FrontUser::getId,
//                FrontUser::getUsername,
//                FrontUser::getEmail);
//        if(StringUtils.hasText(filter)){
//           wrapper.like(FrontUser::getUsername,filter).or()
//                   .like(FrontUser::getEmail,filter);
//        }
//        return baseMapper.selectList(wrapper);
//    }

    @Override
    public FrontUser selectByUsername(String username) {
        LambdaQueryWrapper<FrontUser> wrapper = new LambdaQueryWrapper<>();
        if(username.contains("@")){
            wrapper.eq(FrontUser::getEmail, username);
        }else{
            wrapper.eq(FrontUser::getUsername,username);
        }
        return baseMapper.selectOne(wrapper);
    }

    public FrontUser getUserByUsernameEmail(String username, String email) {
        LambdaQueryWrapper<FrontUser> wrapper= new LambdaQueryWrapper<>();
        wrapper.eq(FrontUser::getUsername,username).eq(FrontUser::getEmail,email);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean resetPassword(FrontUserResetPasswordVo vo) {
        String username = vo.getUsername().trim();
        String email = vo.getEmail().trim();
        FrontUser user = getUserByUsernameEmail(username,email);
        if(!StringUtils.hasText(username) ||
                !StringUtils.hasText(email) || !email.contains("@")){
            throw new CustomizedException(20001,"invalid username and email");
        }
        if(user == null){
            throw new CustomizedException(20001,"Username and email do not match");
        }
        String randomPassword = RandomString.randomCode(10);
        user.setPassword(passwordEncoder.encode(randomPassword));
        ResetPasswordVo passwordVo = new ResetPasswordVo();
        passwordVo.setEmail(user.getEmail());
        passwordVo.setRandomPassword(randomPassword);
        Result<String> result = msmFeign.resetEmail(passwordVo);
        if(result.getSuccess()){
            return true;
        }else{
            throw new CustomizedException(20001,"email send failed");
        }
    }

    @Override
    @Cacheable(value = "frontUser",key = "'id:'+#userId")
    public FrontUserVo getUserById(Long userId) {
        LambdaQueryWrapper<FrontUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FrontUser::getId, userId)
                .select(FrontUser::getId, FrontUser::getEmail, FrontUser::getUsername);
        FrontUser frontUser = baseMapper.selectOne(wrapper);
        FrontUserVo frontUserVo = new FrontUserVo();
        BeanUtils.copyProperties(frontUser,frontUserVo);
        return frontUserVo;
    }
}
