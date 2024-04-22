package com.zz.psmback.web;

import com.zz.psmback.common.utils.AuthenticateUtils;
import com.zz.psmback.common.utils.FileUtils;
import com.zz.psmback.service.impl.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class PsmbackWebApplicationTests {
//    @Autowired
//    private RedisTemplate<String,Object> redistemplate;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    AuthenticationManager  authenticationManager;
//    @Autowired
//    ProjectService projectService;
//    @Autowired
//    UserDetailsService userDetailsService;
//    @Autowired
//    UserService userService;
//    @Autowired
//    RedisUtils redisUtils;
//    @Autowired
//    PsmFileService psmFileService;
//    @Autowired
//    UserDao userDao;
//    @Autowired
//    ServletContext servletContext;
//    @Value("${file-storage.upload-dir}")
//    private String dir;
//    @Autowired
//    TeamDao teamDao;
//    @Autowired
//    TaskDao taskDao;
//    @Autowired
//    AuthenticateUtils authenticateUtils;
//    @Autowired
//    FileUtils fileUtils;
//    @Test
//    void contextLoads() {
//        authenticateUtils.refreshAuthentication();
//    }

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
