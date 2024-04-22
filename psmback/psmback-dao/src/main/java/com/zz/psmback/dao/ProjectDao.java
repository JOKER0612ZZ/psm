package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.Project;
import com.zz.psmback.common.entity.vo.ProjectView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectDao extends BaseMapper<Project> {
    List<ProjectView> queryUserProject(Integer userId);
    Double queryProgress(Integer projectId);
}
