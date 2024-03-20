package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.psmAnnotation.AuthProject;
import com.zz.psmback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/queryUser/{userName}",method = RequestMethod.GET)
    public CommonResult<User> queryUser(@PathVariable("userName") String userName) {
        User user = userService.queryUserByUserName(userName);
        return CommonResult.success(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),user);
    }
    @RequestMapping(value = "/updateNickNameAndGenderById/{userId}/{nickname}/{gender}",method = RequestMethod.GET)
    public CommonResult<?> updateNickNameAndGenderById(@PathVariable("userId") Integer userId, @PathVariable("nickname") String nickname, @PathVariable("gender") String gender) {
        return userService.updateNickNameAndGenderById(userId,nickname,gender);
    }
    @RequestMapping(value = "/updateUserNameById/{userId}/{userName}",method = RequestMethod.GET)
    public CommonResult<?> updateUserNameById(@PathVariable("userId") Integer userId, @PathVariable("userName") String userName) {
        return userService.updateUserNameById(userId,userName);
    }

    @RequestMapping(value = "/updateEmailById/{userId}/{email}",method = RequestMethod.GET)
    public CommonResult<?> updateEmailById(@PathVariable("userId") Integer userId, @PathVariable("email") String email) {
        return userService.updateEmailById(userId,email);
    }
    @RequestMapping(value = "/updatePasswordById/{userId}/{oldPassword}/{newPassword}",method = RequestMethod.GET)
    public CommonResult<?> updatePasswordById(@PathVariable("userId") Integer userId,@
            PathVariable("oldPassword") String oldPassword, @PathVariable("newPassword") String newPassword) {
        return userService.updatePasswordById(userId,oldPassword,newPassword);
    }

    @RequestMapping(value="/auth/{userId}/{projectId}" ,method = RequestMethod.GET)
    @AuthProject("update:project")
    public CommonResult<?> auth(@PathVariable("userId") int userId,@PathVariable int projectId){
        return CommonResult.success(true,111,"成功响应",null);
    }
}
