package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.psmback.common.entity.Project;
import com.zz.psmback.common.entity.Team;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.dao.ProjectDao;
import com.zz.psmback.dao.TeamDao;
import com.zz.psmback.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectDao projectDao;
    @Autowired
    TeamDao teamDao;
    @Override
    public CommonResult<?> createProject(Project project ,String teamName) {
        try{
            QueryWrapper<Project> query = new QueryWrapper<>();
            query.eq("mark",project.getMark());
            Project record = projectDao.selectOne(query);
            if(record != null){
                return CommonResult.error(false, 3010,"项目标记已存在，请使用其他标记",null);
            }
            project.setCreationTime(String.valueOf(LocalDateTime.now()));
            int result = projectDao.insert(project);
            query.eq("mark",project.getMark());
            Project project1 = projectDao.selectOne(query);
            if(result==0){
                return CommonResult.error(false, 3011,"创建项目失败",null);
            }else{
                Team team = new Team(teamName,project1.getCreatorId(),String.valueOf(LocalDateTime.now()),project1.getProjectId());
                int result1 = teamDao.insert(team);
                if(result1==0){
            =        projectDao.deleteById(project1.getProjectId());
                    return CommonResult.error(false, 3011,"创建项目失败",null);
                }
            }
         }catch(Exception e){
            return CommonResult.error(false, 3012,e.getMessage(),null);
        }
        return CommonResult.success(true,3001,"创建项目成功",null);
    }
}
