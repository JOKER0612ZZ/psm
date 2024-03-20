package com.zz.psmback.service.impl;

import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.TaskDao;
import com.zz.psmback.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDao taskDao;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public CommonResult<?> queryTasksByProjectId(int projectId) {
        List<TaskView> taskViews = null;
        try{
            if(redisUtils.hasKey("Project_"+projectId+"_TaskView")){
                taskViews = (List<TaskView>) redisUtils.get("Project_"+projectId+"_Tasks");
            }else{
                taskViews = taskDao.queryTasksByProjectId(projectId);
                redisUtils.set("Project_"+projectId+"_Tasks", taskViews);
                redisUtils.expire("Project_"+projectId+"_Tasks",24*60*60);
            }


        }catch (Exception e){
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
        return CommonResult.success(true,ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(),taskViews);
    }


}
