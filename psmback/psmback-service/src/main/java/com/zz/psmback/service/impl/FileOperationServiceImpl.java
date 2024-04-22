package com.zz.psmback.service.impl;

import com.zz.psmback.common.entity.vo.FileOperationView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.ExcelUtils;
import com.zz.psmback.dao.FileOperationDao;
import com.zz.psmback.service.FileOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.Base64;
import java.util.List;

@Service
public class FileOperationServiceImpl implements FileOperationService {
    @Autowired
    FileOperationDao fileOperationDao;
    @Autowired
    ExcelUtils excelUtils;
    @Override
    public CommonResult<?> getFileOperation(int projectId) {
        try{
            List<FileOperationView> fileOperationViews = fileOperationDao.queryUserFileOperation(projectId);
            byte[] bytes = excelUtils.exportExcel(fileOperationViews);
            String base64String = Base64.getEncoder().encodeToString(bytes);
            return CommonResult.success(ResponseCode.DOWNLOAD_SUCCESS.getCode(),ResponseCode.DOWNLOAD_SUCCESS.getMessage(),base64String);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.DOWNLOAD_ERROR.getCode(), ResponseCode.DOWNLOAD_ERROR.getMessage(),null);
        }
    }
}
