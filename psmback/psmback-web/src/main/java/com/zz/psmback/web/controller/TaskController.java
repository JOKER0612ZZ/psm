package com.zz.psmback.web.controller;

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

}
