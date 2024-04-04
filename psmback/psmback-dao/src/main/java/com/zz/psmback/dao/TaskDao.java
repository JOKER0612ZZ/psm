package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.Task;
import com.zz.psmback.common.entity.vo.TaskView;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface TaskDao extends BaseMapper<Task> {
    List<TaskView> queryTasksByProjectId(int projectId);
    LinkedHashMap<String,Integer> queryStatus(int projectId);
    Integer assignTask(int taskId,int assignerId);
    Integer assignedTask(int taskId);
    Integer updateAssignedTask(int taskId,int assignerId);
    Integer updateTaskStatus(int taskId,String status);
}
