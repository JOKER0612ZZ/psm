package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.psmback.common.entity.Project;
import com.zz.psmback.common.entity.Team;
import com.zz.psmback.common.entity.vo.ProjectDetails;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.ProjectDao;
import com.zz.psmback.dao.TeamDao;
import com.zz.psmback.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 30代表项目
 * 0代表成功，1代表失败
 * 300x代表请求成功，301x代表请求失败
 * **/
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectDao projectDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    RedisUtils redisUtils;
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
                    projectDao.deleteById(project1.getProjectId());
                    return CommonResult.error(false, 3011,"创建项目失败",null);
                }
            }
         }catch(Exception e){
            return CommonResult.error(false, 3012,e.getMessage(),null);
        }
        return CommonResult.success(true,3001,"创建项目成功",null);
    }

    @Override
    public CommonResult<?> queryUserProject(Integer userId) {
        List<ProjectDetails> projects;
        if(redisUtils.hasKey("Project_User_" + userId)){
            projects = (List<ProjectDetails>) redisUtils.Range("Project_User_"+userId);
            log.info("从redis中取得:Project_User_"+userId);
        }else{
            try {
                projects = projectDao.queryUserProject(userId);
                log.info("从Mysql中取得:Project_User_"+userId);
                redisUtils.lPush("Project_User_" + userId, projects);
                redisUtils.expire("Project_User_"+userId,60*60*24);
            }catch (Exception e){
                return CommonResult.error(false, ResponseCode.SELECT_ERROR.getCode(),e.getMessage(),null);
            }
        }
        return CommonResult.success(true, ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), projects);
    }
}
