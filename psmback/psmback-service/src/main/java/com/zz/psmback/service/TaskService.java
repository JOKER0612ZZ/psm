package com.zz.psmback.service;

import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.result.CommonResult;



public interface TaskService {
    CommonResult<?> queryTasksByProjectId(int projectId,int pageNum,int pageSize);
    CommonResult<?> updateTask(TaskView taskView);
    CommonResult<?> insertTask(TaskView taskView);
    CommonResult<?> queryTasksByProjectIdList(int[] projectIdList);
    CommonResult<?> queryTaskStatus(int projectId);
    CommonResult<?> assignTask(int projectId,int taskId,int assignerId);
}
