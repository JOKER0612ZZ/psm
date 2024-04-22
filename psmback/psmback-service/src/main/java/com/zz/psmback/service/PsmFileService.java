package com.zz.psmback.service;

import com.zz.psmback.common.entity.PsmFile;
import com.zz.psmback.common.result.CommonResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface PsmFileService {
    CommonResult<?> uploadFile(MultipartFile[] files, PsmFile psmFile);
    ResponseEntity<?> downloadFile(PsmFile psmFile,int userId, HttpServletResponse response);
    CommonResult<?> queryFileByProjectIdAndParentId(int projectId,Integer parentId);
    CommonResult<?> queryFileByFileId(int fileId);
    CommonResult<?> queryFileByUserId(int userId);
    CommonResult<?> uploadTaskFile(MultipartFile[] files,PsmFile psmFile,Integer taskId);
    CommonResult<?> queryByTaskId(int taskId);
    CommonResult<?> deleteFile(PsmFile psmFile);
}
