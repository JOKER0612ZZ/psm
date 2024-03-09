package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * @author zouzan
 * @date 2024/03/01
 */
@Data
@TableName("user")
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private String email;
    private String nickname;
    private String gender;
    public User(Integer serId, String userName) {
        this.userId = serId;
        this.userName = userName;
    }
    public User() {

    }
}
