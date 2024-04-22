package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zz.psmback.common.entity.PsmFile;
import com.zz.psmback.common.entity.Task;
import com.zz.psmback.common.entity.vo.PsmFileView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.FileUtils;
import com.zz.psmback.dao.PsmFileDao;
import com.zz.psmback.dao.TaskDao;
import com.zz.psmback.service.PsmFileService;
import com.zz.psmback.service_utils.TaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class PsmFileServiceImpl implements PsmFileService {
    @Value("${file-storage.upload-dir}")
    private String dir;
    @Autowired
    PsmFileDao psmFileDao;
    @Autowired
    TaskDao taskDao;
    @Autowired
    TaskUtils taskUtils;
    @Autowired
    FileUtils fileUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> uploadFile(MultipartFile[] files, PsmFile psmFile) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        psmFile.setUploaderTime(formattedDateTime);
        String path = psmFile.getFilePath();
        Path outPath = null;
        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                if (fileName != null && !fileName.isEmpty()) {
                    String pathStr = String.format("%s/%s/%s", dir,path, fileName);
                    Path filePath = Paths.get(pathStr);
                    outPath = filePath;
                    if(Files.exists(filePath)){
                        return CommonResult.error(ResponseCode.FILE_EXISTED.getCode(), fileName+":该文件已存在，请修改文件名。上传已终止", null);
                    }
                    Path storagePath = Paths.get(path, fileName);
                    // 确保目录存在，不存在则创建
                    if(!Files.exists(filePath.getParent())){
                        Files.createDirectories(filePath.getParent());
                        PsmFile folder = new PsmFile(psmFile.getFileName(), psmFile.getFilePath(),
                                psmFile.getUploaderId(),psmFile.getUploaderTime(),psmFile.getProjectId(),"folder", psmFile.getParentId());
                        folder.setFileName(fileUtils.subLastPath(psmFile.getFilePath()));
                        psmFileDao.insert(folder);
                        psmFile.setParentId(folder.getFileId());
                    }
                    // 将文件保存到指定路径
                    file.transferTo(filePath);
                    psmFile.setFilePath(String.valueOf(storagePath));
                    psmFile.setFileName(fileName);
                    psmFileDao.insert(psmFile);
                    psmFileDao.createOperation(psmFile, psmFile.getUploaderId(), "upload");
                }
            }
            return CommonResult.success(ResponseCode.UPLOAD_SUCCESS.getCode(), ResponseCode.UPLOAD_SUCCESS.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            fileUtils.deleteFile(outPath);
            return CommonResult.error(ResponseCode.UPLOAD_FAILED.getCode(), ResponseCode.UPLOAD_FAILED.getMessage(), null);
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> downloadFile(PsmFile psmFile,int userId, HttpServletResponse response) {
        String path = psmFile.getFilePath();
        Path filePath = Paths.get(dir, path);
        log.info("下载路径:"+filePath);
        try {
            // 检查文件是否存在
            if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("不存在此文件");
            }
            // 打开文件输入流
            File file = filePath.toFile();
            FileInputStream inputStream = new FileInputStream(file);

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", file.getName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            psmFileDao.createOperation(psmFile,userId,"download");
            // 返回 ResponseEntity
            return ResponseEntity.status(ResponseCode.DOWNLOAD_SUCCESS.getCode())
                    .headers(headers)
                    .body(new InputStreamResource(inputStream));
        } catch (IOException e) {
            psmFileDao.createOperation(psmFile,userId,"操作失败");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @Override
    public CommonResult<?> queryFileByProjectIdAndParentId(int projectId, Integer parentId) {
        List<PsmFileView> files;
        try{
            files = psmFileDao.queryByProjectIdAndParentId(projectId,parentId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),
                    ResponseCode.SELECT_SUCCESS.getMessage(), files);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),
                    ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }

    @Override
    public CommonResult<?> queryFileByFileId(int fileId) {
        PsmFileView files;
        try{
            files = psmFileDao.queryByFileId(fileId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),
                    ResponseCode.SELECT_SUCCESS.getMessage(), files);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),
                    ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }


    /**
     * 功能描述：查询顶级目录
     *
     * @param userId
     * @return {@code CommonResult<?> }
     * @author zouzan
     * @date 2024/04/07
     */

    @Override
    public CommonResult<?> queryFileByUserId(int userId) {
        List<PsmFile> files = null;
        try {
            files=psmFileDao.queryFileByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),
                    ResponseCode.SELECT_ERROR.getMessage(),null);
        }
        return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),
                ResponseCode.SELECT_SUCCESS.getMessage(), files);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> uploadTaskFile(MultipartFile[] files, PsmFile psmFile, Integer taskId) {
        LambdaUpdateWrapper<Task> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Task::getStatus,"审核中").eq(Task::getTaskId,taskId);
        try{
            psmFile.setTaskId(taskId);
            uploadFile(files,psmFile);
            taskDao.update(null,wrapper);
            taskUtils.updateRedisTask(psmFile.getProjectId());
        }catch(Exception e){
            e.printStackTrace();
            wrapper.set(Task::getStatus,"进行中").eq(Task::getTaskId,taskId);
            taskDao.update(null,wrapper);
            return CommonResult.error(ResponseCode.UPLOAD_FAILED.getCode(),
                    ResponseCode.UPLOAD_FAILED.getMessage(),null);
        }
        return CommonResult.success(ResponseCode.UPLOAD_SUCCESS.getCode(),ResponseCode.UPLOAD_SUCCESS.getMessage(), null);
    }

    @Override
    public CommonResult<?> queryByTaskId(int taskId) {
        try{
            List<PsmFileView> psmFileViews = psmFileDao.queryByTaskId(taskId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(),
                    ResponseCode.SELECT_SUCCESS.getMessage(), psmFileViews);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),
                    ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> deleteFile(PsmFile psmFile) {
        try{
            if(psmFile.getType().equals("folder")){
                fileUtils.deleteFolder(psmFile.getFilePath());
            }else{
                Path path = Paths.get(dir, psmFile.getFilePath());
                fileUtils.deleteFile(path);
            }
            psmFileDao.createOperation(psmFile,psmFile.getUploaderId(),"delete");
            psmFileDao.deleteById(psmFile.getFileId());
            return CommonResult.success(ResponseCode.DELETE_SUCCESS.getCode(),
                    ResponseCode.DELETE_SUCCESS.getMessage(), null);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.DELETE_ERROR.getCode(),
                    ResponseCode.DELETE_ERROR.getMessage(),null);
        }
    }
}
