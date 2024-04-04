package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zz.psmback.common.entity.Task;
import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.PaginationUtils;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.TaskDao;
import com.zz.psmback.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Response;
import java.util.*;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDao taskDao;
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    @Qualifier("paginationUtils")
    PaginationUtils<TaskView> paginationUtils;
    @Override
    public CommonResult<?> queryTasksByProjectId(int projectId, int pageNum, int pageSize) {
        List<TaskView> taskViews;
        List<TaskView> currentTaskViews;
        Map<String, Object> taskMap = new HashMap<>();
        String key = "Project_" + projectId + "_Tasks";
        long total;
        try {
            if (redisUtils.hasKey("Project_" + projectId+"_Tasks") ){
                taskMap = (Map<String, Object>) redisUtils.get(key);
                taskViews = (List<TaskView>) taskMap.get("taskViews");
                currentTaskViews =  paginationUtils.pagination(taskViews,pageNum,pageSize);
                taskMap.put("taskViews",currentTaskViews);
                log.info("从redis取出tasks");
            } else {
                taskViews = taskDao.queryTasksByProjectId(projectId);
                total = taskViews.size();
                taskMap.put("taskViews", taskViews);
                taskMap.put("total", total);
                log.info("从Mysql取出tasks");
                redisUtils.set(key, taskMap);
                redisUtils.expire(key, 24 * 60 * 60);
                currentTaskViews =  paginationUtils.pagination(taskViews,pageNum,pageSize);
                taskMap.put("taskViews",currentTaskViews);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }

        return CommonResult.success(true, ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), taskMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> updateTask(TaskView taskView) {
        Task task = new Task(taskView);
        QueryWrapper<Task> query = new QueryWrapper<>();
        query.eq("task_Id", task.getTaskId());
        Map<String, Object> taskMap = new HashMap<>();
        String key = "Project_" + taskView.getProjectId() + "_Tasks";
        List<TaskView> tasks = null;
        List<TaskView> tasksBackup = null;
        try {
            taskDao.update(task, query);
            taskMap = (Map<String, Object>) redisUtils.get(key);
            tasks = (List<TaskView>) taskMap.get("taskViews");
            tasksBackup = tasks;
            tasks.replaceAll(t -> {
                if (t.getTaskId().equals(taskView.getTaskId())) {
                    return taskView;
                } else {
                    return t;
                } });
            taskMap.put("taskViews", tasks);
            redisUtils.set(key, taskMap);
        } catch (Exception e) {
            if(tasksBackup !=null){
                taskMap.put("taskViews", tasksBackup);
                redisUtils.set(key, taskMap);
            }
            e.printStackTrace();
            return CommonResult.error(ResponseCode.UPDATE_ERROR.getCode(), ResponseCode.UPDATE_ERROR.getMessage(), null);
        }
        return CommonResult.success(true, ResponseCode.UPDATE_SUCCESS.getCode(),
                ResponseCode.UPDATE_SUCCESS.getMessage(), taskMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> insertTask(TaskView taskView) {
        Task task = new Task(taskView);
        List<TaskView> backup = null;
        Map<String, Object> taskMap = new HashMap<>();
        try{
            taskDao.insert(task);
            List<TaskView> taskViews = taskDao.queryTasksByProjectId(taskView.getProjectId());
            taskMap = (Map<String, Object>) redisUtils.get("Project_" + taskView.getProjectId() + "_Tasks");
            backup = (List<TaskView>) taskMap.get("taskViews");
            taskMap.put("total",taskViews.size());
            taskMap.put("taskViews",taskViews);
            redisUtils.set("Project_" + taskView.getProjectId() + "_Tasks", taskMap);
        }catch(Exception e){
            if(backup!=null){
                taskMap.put("taskViews",backup);
                redisUtils.set("Project_" + taskView.getProjectId() + "_Tasks", taskMap);
            }
            e.printStackTrace();
            return CommonResult.error(ResponseCode.INSERT_ERROR.getCode(), ResponseCode.INSERT_ERROR.getMessage(), null);
        }
        return CommonResult.success(true, ResponseCode.INSERT_SUCCESS.getCode(),
                ResponseCode.INSERT_SUCCESS.getMessage(), null);
    }

    @Override
    public CommonResult<?> queryTasksByProjectIdList(int[] projectIdList) {
        List<TaskView> result = new ArrayList<>();
        try{
            for(int projectId:projectIdList){
                result.addAll(loadTaskViews(projectId));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
        return CommonResult.success(true, ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), result);
    }

    @Override
    public CommonResult<?> queryTaskStatus(int projectId) {
        LinkedHashMap<String,Integer> status;
        try{
            status = taskDao.queryStatus(projectId);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
        return CommonResult.success(true, ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), status);
    }

    @Override
    public CommonResult<?> assignTask(int projectId,int taskId, int assignerId) {
        UpdateWrapper<Task> query = new UpdateWrapper<>();
        query.eq("task_id",taskId).set("task_status","进行中");
        String key = "Project_" + projectId + "_Tasks";
        try{
            Integer existed = taskDao.assignedTask(taskId);
            if (existed != null) {
                int updated = taskDao.updateAssignedTask(taskId, assignerId);
                if (updated == 0) {
                    return CommonResult.error(ResponseCode.TASK_ASSIGN_FAILED.getCode(),
                            ResponseCode.TASK_ASSIGN_FAILED.getMessage(), null);
                }
            } else {
                int result = taskDao.assignTask(taskId, assignerId);
                if (result == 0) {
                    return CommonResult.error(ResponseCode.TASK_ASSIGN_FAILED.getCode(),
                            ResponseCode.TASK_ASSIGN_FAILED.getMessage(), null);
                }
                taskDao.updateTaskStatus(taskId,"进行中");
            }
            updateRedisTask(projectId);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.TASK_ASSIGN_FAILED.getCode(),
                    ResponseCode.TASK_ASSIGN_FAILED.getMessage(), null);

        }
        return CommonResult.success(ResponseCode.TASK_ASSIGN_SUCCESS.getCode(),
                ResponseCode.TASK_ASSIGN_SUCCESS.getMessage(), null);
    }

    public List<TaskView> loadTaskViews(int projectId){
        List<TaskView> taskViews;
        try{
            taskViews = taskDao.queryTasksByProjectId(projectId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return taskViews;
    }

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
