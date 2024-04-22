package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.utils.psmAnnotation.AuthProject;
import com.zz.psmback.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @AuthProject("select:task")
    @RequestMapping(value = "/queryTasksByProjectId/{projectId}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public CommonResult<?> queryTasksByProjectId(@PathVariable("projectId") int projectId,
                                                 @PathVariable("pageNum") int pageNum,
                                                 @PathVariable("pageSize") int pageSize) {
        return taskService.queryTasksByProjectId(projectId, pageNum, pageSize);
    }

    @AuthProject("select:task")
    @RequestMapping(value = "/queryTasksByProjectIdMyAssign/{projectId}/{assignerId}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public CommonResult<?> queryTasksByProjectIdMyAssign(@PathVariable("projectId") int projectId,
                                                         @PathVariable("assignerId") int assignerId,
                                                         @PathVariable("pageNum") int pageNum,
                                                         @PathVariable("pageSize") int pageSize) {
        return taskService.queryTasksByProjectIdMyAssign(projectId, assignerId,pageNum, pageSize);
    }

    @AuthProject("select:task")
    @RequestMapping(value = "/queryTasksByProjectIdMyCreate/{projectId}/{creatorId}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public CommonResult<?> queryTasksByProjectIdMyCreate(@PathVariable("projectId") int projectId,
                                                         @PathVariable("creatorId") int creatorId,
                                                         @PathVariable("pageNum") int pageNum,
                                                         @PathVariable("pageSize") int pageSize) {
        return taskService.queryTasksByProjectIdMyCreate(projectId,creatorId, pageNum, pageSize);
    }

    @AuthProject("update:task")
    @RequestMapping(value = "/updateTask/{projectId}", method = RequestMethod.POST)
    public CommonResult<?> updateTask(@PathVariable("projectId") int projectId, @RequestBody TaskView taskView) {
        return taskService.updateTask(taskView);
    }

    @AuthProject("create:task")
    @RequestMapping(value = "/insertTask/{projectId}/{assignerId}", method = RequestMethod.POST)
    public CommonResult<?> insertTask(@PathVariable("projectId") int projectId, @RequestBody TaskView taskView,
                                      @PathVariable("assignerId") int assignerId) {
        return taskService.insertTask(taskView,assignerId);
    }

    @RequestMapping(value = "/queryTasksByProjectIdList", method = RequestMethod.POST)
    public CommonResult<?> queryTasksByProjectIdList(@RequestBody int[] projectIdList) {
        return taskService.queryTasksByProjectIdList(projectIdList);
    }
    @AuthProject("select:task")
    @RequestMapping(value = "/queryTaskStatus/{projectId}", method = RequestMethod.GET)
    public CommonResult<?> queryTaskStatus(@PathVariable("projectId") int projectId) {
        return taskService.queryTaskStatus(projectId);
    }

    @AuthProject("create:task_assign")
    @AuthProject("update:task_assign")
    @RequestMapping(value = "/assignTask/{projectId}/{taskId}/{assignerId}", method = RequestMethod.PUT)
    public CommonResult<?> assignTask(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId, @PathVariable("assignerId") int assignerId) {
        return taskService.assignTask(projectId, taskId, assignerId);
    }

    @AuthProject("update:task")
    @RequestMapping(value = "/updateTaskStatus/{projectId}/{taskId}/{status}", method = RequestMethod.PUT)
    public CommonResult<?> updateTaskStatus(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId, @PathVariable("status") String status) {
        return taskService.updateTaskStatus(projectId, taskId, status);
    }

    @AuthProject("select:task")
    @RequestMapping(value = "/queryVerifyTasks/{projectId}/{userId}", method = RequestMethod.GET)
    public CommonResult<?> queryVerifyTasks(@PathVariable("projectId") int projectId, @PathVariable("userId") int userId) {
        return taskService.queryVerifyTask(projectId, userId);
    }

    @AuthProject("delete:task")
    @RequestMapping(value="/deleteTask/{projectId}/{taskId}", method = RequestMethod.DELETE)
    public CommonResult<?> deleteTask(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId) {
        return taskService.deleteTask(projectId, taskId);
    }
    @AuthProject("select:task")
    @RequestMapping(value="/searchTask/{projectId}/{condition}",method=RequestMethod.GET)
    public CommonResult<?> searchTask(@PathVariable("projectId") int projectId, @PathVariable("condition") String condition){
        return taskService.searchTask(condition, projectId);
    }

    @AuthProject("select:task")
    @RequestMapping(value="/queryStatusByUserId/{projectId}/{userId}",method=RequestMethod.GET)
    public CommonResult<?> queryStatusByUserId(@PathVariable("projectId") Integer projectId, @PathVariable("userId") Integer userId){
        return taskService.queryStatusByUserId(projectId, userId);
    }
}

