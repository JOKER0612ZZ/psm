package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.Project;
import com.zz.psmback.common.entity.vo.ProjectView;
import com.zz.psmback.common.entity.vo.ProjectPlan;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.ExcelUtils;
import com.zz.psmback.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    ExcelUtils excelUtils;
    @RequestMapping("/createProject/{teamName}")
    public CommonResult<?> queryProject(@RequestBody Project project, @PathVariable("teamName") String teamName) {
        return projectService.createProject(project,teamName);
    }
    @RequestMapping(value="/queryUserProject/{userId}",method = RequestMethod.POST)
    public CommonResult<?> queryUserProject(@PathVariable("userId") Integer userId) {
        return projectService.queryUserProject(userId);
    }
    @RequestMapping(value = "/addRecords/{userId}",method = RequestMethod.POST)
    public void addRecords(@PathVariable("userId") Integer userId, @RequestBody ProjectView project) {
        projectService.addRecord(project,userId);
    }
    @RequestMapping(value = "/getRecords/{userId}", method = RequestMethod.GET)
    public CommonResult<?> getRecords(@PathVariable("userId") Integer userId){
        return projectService.getRecords(userId);
    }
    @RequestMapping(value = "/getProgress/{projectId}", method = RequestMethod.GET)
    public CommonResult<?> getProgress(@PathVariable("projectId") Integer projectId){
        return projectService.queryProgress(projectId);
    }
    @RequestMapping(value = "/createFolder/{path}", method = RequestMethod.PUT)
    public boolean createCrateFolder(@PathVariable("path") String path){
        return projectService.createFolder(path);
    }

    @RequestMapping(value="/active/{projectId}/{userId}" ,method = RequestMethod.GET)
    public void active(@PathVariable("projectId") Integer projectId,@PathVariable("userId") Integer userId){
        projectService.active(projectId,userId);
    }
    @RequestMapping(value="/getActive/{projectId}" ,method = RequestMethod.GET)
    public CommonResult<?> getActive(@PathVariable("projectId") Integer projectId){
        return projectService.getActive(projectId);
    }

    @RequestMapping(value="/setPlan/{projectId}" ,method =RequestMethod.POST)
    public CommonResult<?> setPlan(@PathVariable("projectId") Integer projectId,
                                   @RequestParam("plan") MultipartFile file){
        System.out.println(file);
        boolean flag=excelUtils.setPlan(projectId,file);
        if(flag){
            return CommonResult.success(ResponseCode.UPLOAD_SUCCESS.getCode(),
                    ResponseCode.UPLOAD_SUCCESS.getMessage(), null);
        }else{
            return CommonResult.error(ResponseCode.UPLOAD_FAILED.getCode(),
                    ResponseCode.UPLOAD_FAILED.getMessage(), null);
        }
    }

    @RequestMapping(value="/getPlan/{projectId}" ,method =RequestMethod.GET)
    public CommonResult<?> setPlan(@PathVariable("projectId") Integer projectId){
        List<ProjectPlan> plans=excelUtils.getPlan(projectId);
        return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), plans);
    }

    @RequestMapping(value="/updateProject/{projectId}",method = RequestMethod.POST)
    public CommonResult<?> updateProject(@PathVariable("projectId") Integer projectId, @RequestBody Project project){
        return projectService.updateProject(projectId,project);
    }
}
