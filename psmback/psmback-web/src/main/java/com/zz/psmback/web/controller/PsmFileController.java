package com.zz.psmback.web.controller;

import com.alibaba.fastjson.JSON;
import com.zz.psmback.common.entity.PsmFile;
import com.zz.psmback.common.result.CommonResult;
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
        System.out.println(psmFile);
        PsmFile pf= JSON.parseObject(psmFile,PsmFile.class);
        System.out.println(pf);
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
    public CommonResult<?> queryFileByParentId(@PathVariable("fileId") Integer fileId){
        return psmFileService.queryFileByFileId(fileId);
    }
}
