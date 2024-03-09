package com.zz.psmback.service;


import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;
import org.springframework.stereotype.Service;

public interface UserService {
    User queryUserByUserName(String userName);
    User queryUserByUserId(Integer userId);
    CommonResult<?> updateNickNameAndGenderById(Integer userId,String nickname, String gender);
    CommonResult<?> updateUserNameById(Integer userId, String userName);
    CommonResult<?> updateEmailById(Integer userId,String email);

}
