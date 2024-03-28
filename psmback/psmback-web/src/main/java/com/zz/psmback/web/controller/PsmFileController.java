package com.zz.psmback.web.controller;

import com.zz.psmback.common.entity.PsmFile;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.service.PsmFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("/file")
public class PsmFileController {
    @Autowired
    PsmFileService psmFileService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public CommonResult<?> uploadFile(@RequestParam("file") MultipartFile[] files, @RequestBody PsmFile psmFile) {
        return psmFileService.uploadFile(files, psmFile);
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public ResponseEntity<?> downloadFile(@RequestBody PsmFile psmFile, HttpServletResponse response) {
        return psmFileService.downloadFile(psmFile.getFilePath(), response);
    }

    @RequestMapping(value="/queryFile", method = RequestMethod.POST)
    public CommonResult<?> queryFile(@RequestBody PsmFile p) {
        Integer projectId = p.getProjectId();
        Integer parentId = p.getParentId();
        return psmFileService.queryFileByProjectIdAndParentId(projectId, parentId);
    }
    @RequestMapping(value = "/queryFileByFileId/{fileId}", method = RequestMethod.GET)
    public CommonResult<?> queryFileByParentId(@PathVariable("fileId") Integer fileId){
        return psmFileService.queryFileByFileId(fileId);
    }
}
