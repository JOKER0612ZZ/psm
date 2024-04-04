package com.zz.psmback.web;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sun.xml.internal.ws.api.policy.PolicyResolver;
import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.entity.vo.TeamMember;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.utils.JwtUtils;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.TaskDao;
import com.zz.psmback.dao.TeamDao;
import com.zz.psmback.dao.UserDao;
import com.zz.psmback.service.ProjectService;
import com.zz.psmback.service.PsmFileService;
import com.zz.psmback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@SpringBootTest
@Slf4j
class PsmbackWebApplicationTests {
    @Autowired
    private RedisTemplate<String,Object> redistemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager  authenticationManager;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    PsmFileService psmFileService;
    @Autowired
    UserDao userDao;
    @Autowired
    ServletContext servletContext;
    @Value("${file-storage.upload-dir}")
    private String dir;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TaskDao taskDao;
    @Test
    void contextLoads() {
        LinkedHashMap<String,Integer> status = taskDao.queryStatus(4);
        LinkedHashMap<String,Integer> result = new LinkedHashMap<>();
        result.put("未分配",status.get("未分配"));
        result.put("进行中",status.get("进行中"));
        result.put("审核中",status.get("审核中"));
        result.put("已完成",status.get("已完成"));
        result.put("已延期",status.get("已延期"));
        System.out.println(result);
        System.out.println(status);
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
