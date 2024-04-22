package com.zz.psmback.common.utils;

import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUtils {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisUtils redisUtils;
    public LoginUser refreshAuthentication(){
        Authentication nowAuthentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取当前认证用户的凭据
        LoginUser principal = (LoginUser) nowAuthentication.getPrincipal();
        String token = principal.getToken();
        String loginTime = principal.getLoginTime();
        String expireTime = principal.getExpireTime();
        User user=(User) redisUtils.get("login_info_"+principal.getUserId());
        // 使用当前认证用户的凭据创建一个新的认证令牌

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authentication;
        try{
            authentication=authenticationManager.authenticate(authenticationToken);
        }catch (AuthenticationException e){
            e.printStackTrace();
            return null;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginUser result = (LoginUser) authentication.getPrincipal();
        result.setToken(token);
        result.setLoginTime(loginTime);
        result.setExpireTime(expireTime);
        redisUtils.set("UserDetails_"+result.getUserId(), result);
        return result;
    }
}
