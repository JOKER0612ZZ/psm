package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.JwtUtils;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.UserDao;
import com.zz.psmback.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserDao userDao;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public  CommonResult<LoginUser> login(User user) {
        User user1 = null;
        try{
            QueryWrapper<User> query = new QueryWrapper<>();
            query.eq("user_name",user.getUserName());
            user1 = userDao.selectOne(query);
        }catch (Exception e){
            return CommonResult.error(false,500,"数据库未连接",null);
        }
        //获取用户名密码并封装为请求Authentication
        if(user1 == null){
            //返回用户不存在错误
            return CommonResult.error(false,ResponseCode.USER_ACCOUNT_NOT_EXIST.getCode(),
                    ResponseCode.USER_ACCOUNT_NOT_EXIST.getMessage(), null);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),
                user.getPassword());
        Authentication authentication;
        try{
             authentication = authenticationManager.authenticate(authenticationToken);
        }catch(AuthenticationException e){
            return CommonResult.error(false,ResponseCode.USERNAME_PASSWORD_ERROR.getCode(),
                    ResponseCode.USERNAME_PASSWORD_ERROR.getMessage(), null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = JwtUtils.getJwtToken(loginUser);
        loginUser.setToken(token);
        //设置登录时间和失效时间
        loginUser.setLoginTime(String.valueOf(new Date()));
        loginUser.setExpireTime(String.valueOf(new Date(System.currentTimeMillis() + 24*60*60*1000)));
        try {
            //将token和用户信息传到redis上
            redisUtils.set("Token_"+loginUser.getUserName(),token);
            redisUtils.set("UserDetails_"+loginUser.getUserName(),loginUser);
            //设置失效时间
            redisUtils.expire("Token_"+loginUser.getUserName(),24*60*60);
            redisUtils.expire("UserDetails"+loginUser.getUserName(),24*60*60);
            //将用户存入上下文中
        }catch (Exception e){
            return CommonResult.error(false,500,"redis未连接",null);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //返回成功消息
        return CommonResult.success(true,ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(), loginUser);
    }

    @Override
    public CommonResult<?> register(User user) {
        QueryWrapper<User> query = new QueryWrapper<>();
        if(userDao.selectOne(query.eq("user_Name",user.getUserName()))!=null) {
            //返回用户已存在错误
            return CommonResult.error(false,ResponseCode.USER_ACCOUNT_EXISTED.getCode(),
                    ResponseCode.USER_ACCOUNT_EXISTED.getMessage(),null);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Integer result = userDao.insert(user);
        return CommonResult.success(true,ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(), null);
    }

    public CommonResult<LoginUser> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String username = loginUser.getUsername();
        log.info("Authentication"+username);
        //删除redis中存的信息
        redisUtils.delete("Token_" + username);
        redisUtils.delete("UserDetails_" + username);
        //清除上下文
        SecurityContextHolder.clearContext();
        return CommonResult.success(true,ResponseCode.SUCCESS.getCode(),
                "已退出登录，请重新登陆", null);
    }
}
