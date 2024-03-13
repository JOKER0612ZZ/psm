package com.zz.psmback.web;

import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.utils.JwtUtils;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;


@SpringBootTest
@Slf4j
class PsmbackWebApplicationTests {
    @Autowired
    private RedisTemplate<String,Object> redistemplate;
    @Autowired
    private PasswordEncoder PasswordEncoder;
    @Autowired
    AuthenticationManager  authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtils redisUtils;
    @Test
    void contextLoads() {
//        log.info("User_"+userService.queryUserByUserId(6));
//        log.info("User_"+userService.updateNickNameAndGenderById(6,"zouzan","男"));
//        log.info("User_"+userService.updateEmailById(6,"6666"));
//        log.info("User_"+userService.updateUserNameById(6,"zouzan"));
//        log.info("User_"+userService.updateUserNameById(6,"admin"));
        log.info(""+PasswordEncoder.matches("zouzan",userService.queryUserByUserName("admin").getPassword()));
    }

//    @Test
//    public CommonResult<LoginUser> login(User user) {
//        //获取用户名密码并封装为请求Authentication
//        if(userService.queryUserByUserName(user.getUserName())==null){
//            return CommonResult.user_account_not_exist(null);
//        }
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),
//                user.getPassword());
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        if(Objects.isNull(authentication)){
//            return CommonResult.username_password_error(null);
//        }
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        String token = JwtUtils.getJwtToken(loginUser);
//        loginUser.setToken(token);
//        redisUtils.set("Token"+loginUser.getUserName(),token);
//        redisUtils.set("UserDetails"+loginUser.getUserName(),loginUser);
//        //将用户存入上下文中
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        return CommonResult.success(loginUser);
//
//    }

}
