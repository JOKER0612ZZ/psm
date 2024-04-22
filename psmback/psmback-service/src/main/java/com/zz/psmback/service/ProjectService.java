package com.zz.psmback.service;

import com.zz.psmback.common.entity.Project;
import com.zz.psmback.common.entity.vo.ProjectView;
import com.zz.psmback.common.result.CommonResult;

public interface ProjectService {
    CommonResult<?> createProject(Project project,String teamName);
    CommonResult<?> queryUserProject(Integer userId);
    void addRecord(ProjectView projectView, int userId);
    CommonResult<?> getRecords(int userId);
    CommonResult<?> queryProgress(int projectId);
    boolean createFolder(String path);
    void active(Integer projectId,Integer userId);
    CommonResult<?> getActive(Integer projectId);
    CommonResult<?> updateProject(Integer projectId,Project project);
}
