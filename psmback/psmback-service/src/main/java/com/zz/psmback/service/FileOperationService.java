package com.zz.psmback.service;

import com.zz.psmback.common.result.CommonResult;

public interface FileOperationService {
    CommonResult<?> getFileOperation(int projectId);
}
