package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.Project;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @RequestMapping("/createProject/{teamName}")
    public CommonResult<?> queryProject(@RequestBody Project project, @PathVariable("teamName") String teamName) {
        return projectService.createProject(project,teamName);
    }
    @RequestMapping(value="/queryUserProject/{userId}",method = RequestMethod.POST)
    public CommonResult<?> queryUserProject(@PathVariable("userId") Integer userId) {
        return projectService.queryUserProject(userId);
    }
}
