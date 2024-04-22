package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zz.psmback.common.entity.Task;
import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.DateTimeFormatterUtils;
import com.zz.psmback.common.utils.PaginationUtils;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.TaskDao;
import com.zz.psmback.service.TaskService;
import com.zz.psmback.service_utils.TaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author zouzan
 * @date 2024/04/05
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDao taskDao;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    TaskUtils taskUtils;
    @Autowired
    @Qualifier("paginationUtils")
    PaginationUtils<TaskView> paginationUtils;

    /**
     * 功能描述：
     *
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/05
     */

    @Override
    @SuppressWarnings("unchecked")
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

        return CommonResult.success( ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), taskMap);
    }

    /**
     * 功能描述：修改任务
     *
     * @param taskView
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/05
     */

    @Override
    @SuppressWarnings("unchecked")
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
        return CommonResult.success(ResponseCode.UPDATE_SUCCESS.getCode(),
                ResponseCode.UPDATE_SUCCESS.getMessage(), taskMap);
    }

    /**
     * 功能描述：新增任务
     *
     * @param taskView
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/05
     */

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> insertTask(TaskView taskView,int assignerId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        Task task = new Task(taskView);
        task.setDeadline(DateTimeFormatterUtils.format(task.getDeadline()));
        task.setCreationTime(formattedDateTime);
        if(task.getCreatorId()!=null){
            task.setStatus("进行中");
        }
        List<TaskView> backup = null;
        Map<String, Object> taskMap = new HashMap<>();
        try{
            taskDao.insert(task);
            taskDao.assignTask(task.getTaskId(),assignerId);
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
        return CommonResult.success( ResponseCode.INSERT_SUCCESS.getCode(),
                ResponseCode.INSERT_SUCCESS.getMessage(), null);
    }

    /**
     * 功能描述：根据一个projectList查找所有task
     *
     * @param projectIdList
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/05
     */

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
        return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), result);
    }

    /**
     * 功能描述：
     *
     * @param projectId
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/05
     */

    @Override
    public CommonResult<?> queryTaskStatus(int projectId) {
        LinkedHashMap<String,Integer> status;
        try{
            status = taskDao.queryStatus(projectId);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
        return CommonResult.success( ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), status);
    }

    /**
     * 功能描述：
     *
     * @param projectId
     * @param taskId
     * @param assignerId
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/05
     */

    @Override
    public CommonResult<?> assignTask(int projectId,int taskId, int assignerId) {
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
            taskUtils.updateRedisTask(projectId);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.TASK_ASSIGN_FAILED.getCode(),
                    ResponseCode.TASK_ASSIGN_FAILED.getMessage(), null);

        }
        return CommonResult.success(ResponseCode.TASK_ASSIGN_SUCCESS.getCode(),
                ResponseCode.TASK_ASSIGN_SUCCESS.getMessage(), null);
    }

    /**
     * 功能描述：更改任务状态
     *
     * @param taskId
     * @param status
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/06
     */

    @Override
    public CommonResult<?> updateTaskStatus(int projectId,int taskId, String status) {
        try{
            int result = taskDao.updateTaskStatus(taskId, status);
            if (result == 0) {
                return CommonResult.error(ResponseCode.UPDATE_ERROR.getCode(),
                        ResponseCode.UPDATE_ERROR.getMessage(), null);
            }
            taskUtils.updateRedisTask(projectId);
        }catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.UPDATE_ERROR.getCode(),
                    ResponseCode.UPDATE_ERROR.getMessage(), null);
        }
        return CommonResult.success(ResponseCode.UPDATE_SUCCESS.getCode(),
                ResponseCode.UPDATE_SUCCESS.getMessage(), null);
    }

    @Override
    public CommonResult<?> queryTasksByProjectIdMyAssign(int projectId, int assignerId, int pageNum, int pageSize) {
        Page<TaskView> page = new Page<>(pageNum,pageSize);
        try{
            Page<TaskView> taskPages=taskDao.queryTasksMyAssigned(page,projectId,assignerId);
            return getCommonResult(taskPages);
        }catch (Exception e){
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
    }

    @Override
    public CommonResult<?> queryTasksByProjectIdMyCreate(int projectId, int creatorId, int pageNum, int pageSize) {
        Page<TaskView> page = new Page<>(pageNum,pageSize);
        try{
            Page<TaskView> taskPages=taskDao.queryTasksMyCreated(page,projectId,creatorId);
            return getCommonResult(taskPages);
        }catch (Exception e){
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
    }

    /**
     * 功能描述：返回分页后的数据
     *
     * @param taskPages
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/06
     */

    public CommonResult<?> getCommonResult(Page<TaskView> taskPages) {
        List<TaskView> tasks = taskPages.getRecords();
        long total = taskPages.getTotal();
        Map<String,Object> taskMaps = new HashMap<>();
        taskMaps.put("taskViews",tasks);
        taskMaps.put("total",total);
        return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), taskMaps);
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

    /**
     * 功能描述：定时检查并更新task的状态
     *
     * @author zouzan
     * @date 2024/04/07
     */

    @Scheduled(initialDelay = 10000, fixedRate = 60000)
    public void checkAndUpdateTaskStatus() {
        List<Task> tasks = taskDao.selectList(null);
        LocalDateTime now = LocalDateTime.now();
        for (Task task : tasks) {
            LocalDateTime deadlineDateTime = LocalDateTime.parse(task.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (deadlineDateTime.isBefore(now) && !task.getStatus().equals("已完成")) {
                taskDao.updateTaskStatus(task.getTaskId(),"已截止");
                taskUtils.updateRedisTask(task.getProjectId());
            }
        }
//        for (Task task : tasks) {
//            LocalDateTime deadlineDateTime = LocalDateTime.parse(task.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            if (now.isBefore(deadlineDateTime) && !task.getStatus().equals("已完成") && !task.getStatus().equals("审核中")){
//                taskDao.updateTaskStatus(task.getTaskId(),"进行中");
//                taskUtils.updateRedisTask(task.getProjectId());
//            }
//        }
    }

    /**
     * 功能描述：查询审核中的任务
     *
     * @param projectId
     * @param userId
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/12
     */

    @Override
    public CommonResult<?> queryVerifyTask(int projectId, int userId) {
        try{
            List<TaskView> tasks = taskDao.queryVerifyTasks(projectId, userId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),ResponseCode.SELECT_SUCCESS.getMessage(),tasks);
        }catch(Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }

    @Override
    public CommonResult<?> deleteTask(int projectId, int taskId) {
        try{
            taskDao.deleteById(taskId);

            taskUtils.updateRedisTask(projectId);
            return CommonResult.success(ResponseCode.DELETE_SUCCESS.getCode(), ResponseCode.DELETE_SUCCESS.getMessage(), null);
        }catch(Exception e){
            return CommonResult.error(ResponseCode.DELETE_ERROR.getCode(), ResponseCode.DELETE_ERROR.getMessage(), null);
        }
    }

    @Override
    public CommonResult<?> searchTask(String condition, int projectId) {
        try{
            List<TaskView> tasks = taskDao.searchTask(condition,projectId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),ResponseCode.SELECT_SUCCESS.getMessage(),tasks);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }

    /**
     * 功能描述：查询用户负责的任务
     *
     * @param projectId
     * @param userId
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/21
     */

    @Override
    public CommonResult<?> queryStatusByUserId(int projectId, int userId) {
        try{
            Map<String,Integer> status = taskDao.queryStatusByUserId(projectId,userId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),ResponseCode.SELECT_SUCCESS.getMessage(),status);
        }catch (Exception e){
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),"用户工作情况"+ResponseCode.SELECT_ERROR.getMessage(), null);
        }
    }
}
