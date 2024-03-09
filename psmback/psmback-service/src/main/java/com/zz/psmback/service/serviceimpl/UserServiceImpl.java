package com.zz.psmback.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.dao.UserDao;
import com.zz.psmback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User queryUserByUserId(Integer userId) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_id",userId);
        return userDao.selectOne(query);
    }

    @Override
    public CommonResult<User> updateNickNameAndGenderById(Integer userId, String nickname, String gender) {
        UpdateWrapper<User> warp = new UpdateWrapper<>();
        warp.set("nickname",nickname).set("gender",gender).eq("user_id",userId);
        if(userDao.update(null, warp)==0) {
            return CommonResult.error(false,2033,"更改出错",null);
        }
        return CommonResult.success(true,1031,"更改完成",queryUserByUserId(userId));
    }

    @Override
    public CommonResult<User> updateUserNameById(Integer userId, String userName) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_name",userName);
        if(userDao.selectOne(query)!=null) {
            return CommonResult.error(false, ResponseCode.USER_ACCOUNT_EXISTED.getCode(),
                    ResponseCode.USER_ACCOUNT_EXISTED.getMessage(),null);
        }
        UpdateWrapper<User> warp = new UpdateWrapper<>();
        warp.set("user_name",userName).eq("user_id",userId);
        if(userDao.update(null, warp)==0) {
            return CommonResult.error(false,2033,"更改出错",null);
        }
        return CommonResult.success(true,1032,"更改完成",queryUserByUserId(userId));
    }

    @Override
    public CommonResult<?> updateEmailById(Integer userId, String email) {
        UpdateWrapper<User> warp = new UpdateWrapper<>();
        warp.set("email",email).eq("user_id",userId);
        if(userDao.update(null, warp)==0) {
            return CommonResult.error(false,2033,"更改出错",null);
        }
        return CommonResult.success(true,1033,"更改完成",queryUserByUserId(userId));
    }

    @Override
    public User queryUserByUserName(String userName) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_name",userName);
        return userDao.selectOne(query);
    }
}
