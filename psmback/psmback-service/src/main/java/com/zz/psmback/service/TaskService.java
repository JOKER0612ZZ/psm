package com.zz.psmback.service;

import com.zz.psmback.common.result.CommonResult;



public interface TaskService {
    CommonResult<?> queryTasksByProjectId(int projectId);
}
