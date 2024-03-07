package com.zz.psmback.service;

import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.result.CommonResult;


public interface LoginService {
    CommonResult<LoginUser> login(User user);
    CommonResult<?> logout();
    CommonResult<?> register(User user);
}
