package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_name",username);
        User user = userDao.selectOne(query);
        if (user == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user, loginUser);
        return loginUser;
    }
}

