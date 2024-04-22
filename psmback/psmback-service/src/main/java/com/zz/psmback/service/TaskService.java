package com.zz.psmback.service;

import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.result.CommonResult;



public interface TaskService {
    CommonResult<?> queryTasksByProjectId(int projectId,int pageNum,int pageSize);
    CommonResult<?> queryTasksByProjectIdMyAssign(int projectId,int assignerId,int pageNum,int pageSize);
    CommonResult<?> queryTasksByProjectIdMyCreate(int projectId,int creatorId,int pageNum,int pageSize);
    CommonResult<?> updateTask(TaskView taskView);
    CommonResult<?> insertTask(TaskView taskView,int assignerId);
    CommonResult<?> queryTasksByProjectIdList(int[] projectIdList);
    CommonResult<?> queryTaskStatus(int projectId);
    CommonResult<?> assignTask(int projectId,int taskId,int assignerId);
    CommonResult<?> updateTaskStatus(int projectId,int taskId,String status);
    CommonResult<?> queryVerifyTask(int projectId,int taskId);
    CommonResult<?> deleteTask(int projectId,int taskId);
    CommonResult<?> searchTask(String condition,int projectId);
    CommonResult<?> queryStatusByUserId(int projectId,int userId);
}
