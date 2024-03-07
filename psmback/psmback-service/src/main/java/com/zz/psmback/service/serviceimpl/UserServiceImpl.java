package com.zz.psmback.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.dao.UserDao;
import com.zz.psmback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

//    public CommonResult<User> login(User user) {
//        User user1 = queryUserByUserName(user.getUserName());
//        if(user1!=null){
//            if(user1.getPassword().equals(user.getPassword())){
//                return CommonResult.success(user1);
//            }
//        }
//        return CommonResult.error(null);
//    }

    @Override
    public User queryUserByUserName(String userName) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_name",userName);
        return userDao.selectOne(query);
    }
}
