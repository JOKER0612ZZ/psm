package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.utils.AuthenticateUtils;
import com.zz.psmback.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    AuthenticateUtils authenticateUtils;
    @PostMapping("/login")
    public CommonResult<LoginUser> login(@RequestBody User user) {
        return loginService.login(user);
    }

    @RequestMapping("/register")
    public CommonResult<?> register(@RequestBody User user) {
        return loginService.register(user);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public CommonResult<?> logout() {
        return loginService.logout();
    }
}
