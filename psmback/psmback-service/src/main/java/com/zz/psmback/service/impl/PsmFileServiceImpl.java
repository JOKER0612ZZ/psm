package com.zz.psmback.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.psmback.common.entity.PsmFile;
import com.zz.psmback.common.entity.vo.PsmFileView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.dao.PsmFileDao;
import com.zz.psmback.service.PsmFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PsmFileServiceImpl implements PsmFileService {
    @Value("${file-storage.upload-dir}")
    private String dir;
    @Autowired
    PsmFileDao psmFileDao;
    @Autowired
    ResourceLoader resourceLoader;
    @Override
    public void createFolder(String path) {
        File file = new File(dir+path);
        boolean flag=false;
        if(!file.exists()){
            flag =  file.mkdirs();
        }
        log.info("Creating folder:"+flag);
    }

    @Override
    public CommonResult<?> uploadFile(MultipartFile[] files, PsmFile psmFile) {
        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                if (fileName != null && !fileName.isEmpty()) {
                    Path filePath = Paths.get(dir, psmFile.getFilePath(), fileName);
                    // 确保目录存在，不存在则创建
                    Files.createDirectories(filePath.getParent());
                    // 将文件保存到指定路径
                    file.transferTo(filePath);
                }
            }
            psmFileDao.insert(psmFile);
            return CommonResult.success(true, 11111, "上传成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(22222, "上传失败", null);
        }
    }

    @Override
    public ResponseEntity<?> downloadFile(String path,HttpServletResponse response) {
        Path filePath = Paths.get(dir, path);
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

            // 返回 ResponseEntity
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }


    @Override
    public CommonResult<?> queryFileByProjectIdAndParentId(int projectId, Integer parentId) {
        List<PsmFileView> files;
        try{
            files = psmFileDao.queryByProjectIdAndParentId(projectId,parentId);
            return CommonResult.success(true,ResponseCode.SELECT_SUCCESS.getCode(),
                    ResponseCode.SELECT_SUCCESS.getMessage(), files);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),
                    ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }

    @Override
    public CommonResult<?> queryFileByFileId(int fileId) {
        List<PsmFileView> files;
        try{
            files = psmFileDao.queryByFileId(fileId);
            return CommonResult.success(true,ResponseCode.SELECT_SUCCESS.getCode(),
                    ResponseCode.SELECT_SUCCESS.getMessage(), files);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),
                    ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }
}
