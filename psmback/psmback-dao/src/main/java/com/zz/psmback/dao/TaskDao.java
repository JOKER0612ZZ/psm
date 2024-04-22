package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zz.psmback.common.entity.Task;
import com.zz.psmback.common.entity.vo.TaskView;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Mapper
public interface TaskDao extends BaseMapper<Task> {
    LinkedList<TaskView> queryTasksByProjectId(int projectId);
    Page<TaskView> queryTasksMyAssigned(Page<TaskView> page, int projectId, int assignerId);
    Page<TaskView> queryTasksMyCreated(Page<TaskView> page,int projectId,int creatorId);
    LinkedHashMap<String,Integer> queryStatus(int projectId);
    Integer assignTask(int taskId,int assignerId);
    Integer assignedTask(int taskId);
    Integer updateAssignedTask(int taskId,int assignerId);
    Integer updateTaskStatus(int taskId,String status);
    List<TaskView> queryVerifyTasks(int projectId,int userId);
    List<TaskView> searchTask(String condition,int projectId);
    LinkedHashMap<String,Integer> queryStatusByUserId(int projectId,int userId);
}
