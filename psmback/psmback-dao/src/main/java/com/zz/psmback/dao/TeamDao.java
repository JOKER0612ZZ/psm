package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.Team;
import com.zz.psmback.common.entity.vo.TeamView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamDao extends BaseMapper<Team> {
    List<TeamView> queryByUserId(int userId);
}
