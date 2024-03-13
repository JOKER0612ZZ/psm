package com.zz.psmback.web;

import com.zz.psmback.common.entity.User;
import com.zz.psmback.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Atest {

    @Test
    void contextLoads() {
        UserServiceImpl userService = new UserServiceImpl();
        User user=userService.queryUserByUserName("admin");
    }

}
