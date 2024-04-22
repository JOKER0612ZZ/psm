package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.FileOperation;
import com.zz.psmback.common.entity.vo.FileOperationView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileOperationDao extends BaseMapper<FileOperation> {
    List<FileOperationView> queryUserFileOperation(Integer projectId);
}
