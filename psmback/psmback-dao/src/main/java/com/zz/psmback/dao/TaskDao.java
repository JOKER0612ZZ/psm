package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.Task;
import com.zz.psmback.common.entity.vo.TaskView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskDao extends BaseMapper<Task> {
    List<TaskView> queryTasksByProjectId(int projectId);
}
