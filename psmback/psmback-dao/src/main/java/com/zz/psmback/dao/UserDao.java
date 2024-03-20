package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.Authority;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.entity.vo.ProjectAuthorities;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {
    int assignRole(int userId,int roleId);
    List<ProjectAuthorities> queryProAuthorities(int userId);
    List<Authority> queryUserAuthorities(int userId);
}
