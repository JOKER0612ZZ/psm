package com.zz.psmback.web.controller;

import com.alibaba.fastjson.JSON;
import com.zz.psmback.common.entity.PsmFile;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.psmAnnotation.AuthProject;
import com.zz.psmback.service.PsmFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@CrossOrigin("*")
@RequestMapping("/file")
public class PsmFileController {
    @Autowired
    PsmFileService psmFileService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public CommonResult<?> uploadFile(@RequestParam("file") MultipartFile[] files, @RequestParam("psmfile") String psmFile) {
        PsmFile pf= JSON.parseObject(psmFile,PsmFile.class);
        return psmFileService.uploadFile(files, pf);
    }

    @RequestMapping(value = "/downloadFile/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> downloadFile(@RequestBody PsmFile psmFile,
                                                          @PathVariable("userId") int userId,
                                                          HttpServletResponse response) {
        return psmFileService.downloadFile(psmFile, userId,response);
    }

    @RequestMapping(value="/queryFile", method = RequestMethod.POST)
    public CommonResult<?> queryFile(@RequestBody PsmFile p) {
        Integer projectId = p.getProjectId();
        Integer parentId = p.getParentId();
        return psmFileService.queryFileByProjectIdAndParentId(projectId, parentId);
    }
    @RequestMapping(value = "/queryFileByFileId/{fileId}", method = RequestMethod.GET)
    public CommonResult<?> queryFileByParentId(@PathVariable(name="fileId",required = false) String fileIdStr){
        Integer fileId = null;
        if (fileIdStr != null && !fileIdStr.equalsIgnoreCase("null")) {
            try {
                fileId = Integer.valueOf(fileIdStr);
                return psmFileService.queryFileByFileId(fileId);
            } catch (NumberFormatException e) {
                return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), null);
            }
        }
        return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), null);
    }
    @RequestMapping(value ="/queryFileByUserId/{userId}", method = RequestMethod.GET)
    public CommonResult<?> queryFileByUserId(@PathVariable("userId") int userId){
        return psmFileService.queryFileByUserId(userId);
    }
    @RequestMapping(value = "/uploadTaskFile/{taskId}", method = RequestMethod.POST)
    public CommonResult<?> uploadTaskFile(@RequestParam("file") MultipartFile[] files, @RequestParam("psmfile") String psmFile,
                                          @PathVariable("taskId") Integer taskId) {
        PsmFile pf= JSON.parseObject(psmFile,PsmFile.class);
        return psmFileService.uploadTaskFile(files,pf,taskId);
    }
    @RequestMapping(value = "/queryByTaskId/{taskId}", method = RequestMethod.GET)
    public CommonResult<?> queryByTaskId(@PathVariable("taskId") Integer taskId){
        return psmFileService.queryByTaskId(taskId);
    }

    @AuthProject("delete:file")
    @RequestMapping(value = "/deleteFile/{projectId}", method = RequestMethod.POST)
    public CommonResult<?> deleteFile(@PathVariable("projectId") int projectId,@RequestBody PsmFile psmFile) {
        return psmFileService.deleteFile(psmFile);
    }

}
