package com.zz.psmback.service;

import com.zz.psmback.common.entity.Project;
import com.zz.psmback.common.result.CommonResult;
import org.springframework.stereotype.Service;

public interface ProjectService {
    CommonResult<?> createProject(Project project,String teamName);
    CommonResult<?> queryUserProject(Integer userId);
}
