package com.zz.psmback.service_utils;

import com.zz.psmback.common.entity.vo.ProjectView;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectUtils {
    @Autowired
    private  ProjectDao projectDao;
    @Autowired
    private  RedisUtils redisUtils;
    @SuppressWarnings("unchecked")
    public  void updateRedis(int userId){
        String key = "Project_User_"+userId;
        List<ProjectView> backup= (List<ProjectView>) redisUtils.Range("Project_User_" + userId);
        try{
            List<ProjectView> projects = projectDao.queryUserProject(userId);
            redisUtils.delete(key);
            redisUtils.lPush("Project_User_"+userId, projects);
            redisUtils.expire("Project_User_"+userId, 60 * 60 * 24);
        }catch (Exception e){
            redisUtils.delete(key);
            redisUtils.lPush(key,backup);
            e.printStackTrace();
        }
    }
}
