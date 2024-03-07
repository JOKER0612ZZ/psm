package com.zz.psmback.web;

import com.zz.psmback.common.entity.User;
import com.zz.psmback.service.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class Atest {

    @Test
    void contextLoads() {
        UserServiceImpl userService = new UserServiceImpl();
        User user=userService.queryUserByUserName("admin");
    }

}
