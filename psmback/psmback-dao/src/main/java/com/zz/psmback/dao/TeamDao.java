package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.Team;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamDao extends BaseMapper<Team> {
}
