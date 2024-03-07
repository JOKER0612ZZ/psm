package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
