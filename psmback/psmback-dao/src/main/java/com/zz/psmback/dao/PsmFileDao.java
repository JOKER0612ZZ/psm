package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.PsmFile;
import com.zz.psmback.common.entity.vo.PsmFileView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PsmFileDao extends BaseMapper<PsmFile> {
    List<PsmFileView> queryByProjectIdAndParentId(int projectId, Integer parentId);
    List<PsmFileView> queryByFileId(int fileId);
}
