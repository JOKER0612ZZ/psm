package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.UserDao;
import com.zz.psmback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RedisUtils redisUtils;
    @Override
    public User queryUserByUserId(Integer userId) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_id",userId);
        try{
            return userDao.selectOne(query);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CommonResult<User> updateNickNameAndGenderById(Integer userId, String nickname, String gender) {
        UpdateWrapper<User> warp = new UpdateWrapper<>();
        warp.set("nickname",nickname).set("gender",gender).eq("user_id",userId);
        if(userDao.update(null, warp)==0) {
            return CommonResult.error(ResponseCode.UPDATE_ERROR.getCode(),ResponseCode.UPDATE_ERROR.getMessage(), null);
        }
        try{
            User user=queryUserByUserId(userId);
            return CommonResult.success(ResponseCode.UPDATE_SUCCESS.getCode(),ResponseCode.UPDATE_SUCCESS.getMessage(),user);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.UPDATE_ERROR.getCode(),ResponseCode.UPDATE_ERROR.getMessage(), null);
        }
    }

    @Override
    public CommonResult<User> updateUserNameById(Integer userId, String userName) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_name",userName);
        if(userDao.selectOne(query)!=null) {
            return CommonResult.error( ResponseCode.USER_ACCOUNT_EXISTED.getCode(),
                    ResponseCode.USER_ACCOUNT_EXISTED.getMessage(),null);
        }
        UpdateWrapper<User> warp = new UpdateWrapper<>();
        warp.set("user_name",userName).eq("user_id",userId);
        if(userDao.update(null, warp)==0) {
            return CommonResult.error(2033,"更改出错",null);
        }

        return CommonResult.success(1032,"更改完成",queryUserByUserId(userId));
    }

    @Override
    public CommonResult<?> updateEmailById(Integer userId, String email) {
        UpdateWrapper<User> warp = new UpdateWrapper<>();
        warp.set("email",email).eq("user_id",userId);
        if(userDao.update(null, warp)==0) {
            return CommonResult.error(2033,"更改出错",null);
        }
        return CommonResult.success(1033,"更改完成",queryUserByUserId(userId));
    }

    @Override
    public User queryUserByUserName(String userName) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_name",userName);
        return userDao.selectOne(query);
    }

    @Override
    public CommonResult<?> updatePasswordById(Integer userId, String oldPassword, String newPassword) {
        User user = queryUserByUserId(userId);
        if(!passwordEncoder.matches(oldPassword,user.getPassword())) {
            return CommonResult.error(2034,"旧密码输入错误",null);
        }
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        String password= passwordEncoder.encode(newPassword);
        wrapper.set("password",password).eq("user_id",userId);
        if(userDao.update(null, wrapper)==0) {
            return CommonResult.error(2033,"更改出错",null);
        }
        user.setPassword(password);
        return CommonResult.success(1034,"密码更改成功",user);
    }

    @Override
    public CommonResult<?> searchUser(String condition) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.like(User::getUserName,condition);
        List<User> users =null;
        try{
            users=userDao.selectList(query);
            log.info("Users:"+users);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), users);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
    }

    public LoginUser loadUser(String username){
        try {
            return (LoginUser) redisUtils.get("UserDetails_"+username);
        }catch (Exception e){
            return null;
        }
    }
}
