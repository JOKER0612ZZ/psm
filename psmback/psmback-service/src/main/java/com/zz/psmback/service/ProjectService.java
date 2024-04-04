package com.zz.psmback.service;

import com.zz.psmback.common.entity.Project;
import com.zz.psmback.common.entity.vo.ProjectDetails;
import com.zz.psmback.common.result.CommonResult;
import org.springframework.stereotype.Service;

public interface ProjectService {
    CommonResult<?> createProject(Project project,String teamName);
    CommonResult<?> queryUserProject(Integer userId);
    void addRecord(ProjectDetails projectDetails,int userId);
    CommonResult<?> getRecords(int userId);
    CommonResult<?> queryProgress(int projectId);
}
