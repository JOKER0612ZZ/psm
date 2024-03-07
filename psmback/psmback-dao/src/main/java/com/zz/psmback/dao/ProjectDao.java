package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.Project;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectDao extends BaseMapper<Project> {
}
