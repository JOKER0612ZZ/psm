package com.zz.psmback.service;

import com.zz.psmback.common.entity.PsmFile;
import com.zz.psmback.common.result.CommonResult;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface PsmFileService {
    void createFolder(String path);
    CommonResult<?> uploadFile(MultipartFile[] files, PsmFile psmFile);
    ResponseEntity<?> downloadFile(PsmFile psmFile,int userId, HttpServletResponse response);
    CommonResult<?> queryFileByProjectIdAndParentId(int projectId,Integer parentId);
    CommonResult<?> queryFileByFileId(int fileId);

}
