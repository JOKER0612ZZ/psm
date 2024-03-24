package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.vo.TaskView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.utils.psmAnnotation.AuthProject;
import com.zz.psmback.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        return taskService.queryTasksByProjectId(projectId,pageNum,pageSize);
    }

    @AuthProject("update:task")
    @RequestMapping(value = "/updateTask/{projectId}", method = RequestMethod.POST)
    public CommonResult<?> updateTask(@PathVariable("projectId") int projectId ,@RequestBody TaskView taskView) {
        return taskService.updateTask(taskView);
    }
    @AuthProject("insert:task")
    @RequestMapping(value = "/insertTask/{projectId}", method = RequestMethod.POST)
    public CommonResult<?> insertTask(@PathVariable("projectId") int projectId,@RequestBody TaskView taskView) {
        return taskService.insertTask(taskView);
    }
}
