package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.TaskDao;
import com.zz.psmback.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDao taskDao;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public CommonResult<?> queryTasksByProjectId(int projectId,int pageNum,int pageSize) {
        List<TaskView> taskViews;
        Page<TaskView> page = new Page<>(pageNum, pageSize);
        Map<String,Object> taskView = new HashMap<>();
        long total;
        try{
            if(redisUtils.hasKey("Project_"+projectId+"_Page_"+pageNum+"_Tasks_"+pageSize)){
                taskView = (Map<String,Object>) redisUtils.get("Project_"+projectId+"_Page_"+pageNum+"_Tasks_"+pageSize);
                log.info("从redis取出tasks");
            }else{
                Page<TaskView> taskPage = taskDao.queryTasksByProjectId(page, projectId);
                taskViews = taskPage.getRecords();
                total = taskPage.getTotal();
                taskView.put("taskViews", taskViews);
                taskView.put("total", total);
                log.info("从Mysql取出tasks");
                redisUtils.set("Project_"+projectId+"_Page_"+pageNum+"_Tasks_"+pageSize, taskView);
                redisUtils.expire("Project_"+projectId+"_Page_"+pageNum+"_Tasks_"+pageSize,24*60*60);
            }
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }

        return CommonResult.success(true,ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(),taskView);
    }


}
