package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.FileOperation;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.service.FileOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operation")
public class FileOperationController {
    @Autowired
    FileOperationService fileOperationService;
    @RequestMapping(value = "/getOperations/{projectId}", method = RequestMethod.GET)
    public CommonResult<?> getOperations(@PathVariable("projectId") int projectId) {
        return fileOperationService.getFileOperation(projectId);
    }
}
