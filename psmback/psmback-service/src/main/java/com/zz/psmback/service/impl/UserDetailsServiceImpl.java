package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.psmback.common.entity.Authority;
import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.entity.vo.ProjectAuthorities;
import com.zz.psmback.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        List<ProjectAuthorities> projectAuthorities = userDao.queryProAuthorities(loginUser.getUserId());
        List<Authority> authorities = userDao.queryUserAuthorities(loginUser.getUserId());
        loginUser.setProjectAuthorities(projectAuthorities);
        loginUser.setAuthorities(authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList()));
        log.info("authorities: "+authorities+"");
        return loginUser;
    }
}

