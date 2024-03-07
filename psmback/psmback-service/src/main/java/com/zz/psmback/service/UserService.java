package com.zz.psmback.service;


import com.zz.psmback.common.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User queryUserByUserName(String userName);
}
