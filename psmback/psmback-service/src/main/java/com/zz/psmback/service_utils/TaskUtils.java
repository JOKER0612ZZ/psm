package com.zz.psmback.service_utils;

import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskUtils {
    @Autowired
    TaskDao taskDao;
    @Autowired
    RedisUtils redisUtils;
    public void updateRedisTask(int projectId){
        List<TaskView> taskViews = taskDao.queryTasksByProjectId(projectId);
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("taskViews", taskViews);
        taskMap.put("total", taskViews.size());
        String key = "Project_" + projectId + "_Tasks";
        redisUtils.set(key, taskMap);
        redisUtils.expire(key, 24 * 60 * 60);
    }
}
