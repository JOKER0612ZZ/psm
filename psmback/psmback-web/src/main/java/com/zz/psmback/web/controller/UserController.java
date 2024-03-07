package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/queryUser/{userName}",method = RequestMethod.GET)
    public CommonResult<User> queryUser(@PathVariable("userName") String userName) {
        User user = userService.queryUserByUserName(userName);
        return CommonResult.success(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),user);
    }
}
