package com.zz.psmback.service;


import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;

public interface UserService {
    User queryUserByUserName(String userName);
    User queryUserByUserId(Integer userId);
    CommonResult<?> updateNickNameAndGenderById(Integer userId,String nickname, String gender);
    CommonResult<?> updateUserNameById(Integer userId, String userName);
    CommonResult<?> updateEmailById(Integer userId,String email);
    CommonResult<?> updatePasswordById(Integer userId,String oldPassword,String newPassword);
    CommonResult<?> searchUser(String condition);
}
