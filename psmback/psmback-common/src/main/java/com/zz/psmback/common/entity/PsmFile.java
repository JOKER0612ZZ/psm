package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("file")
public class PsmFile {
    private Integer fileId;
    private String fileName;
    private String filePath;
    private String uploaderId;
    private String uploaderTime;
    private Integer projectId;
    private String type;
    private Integer parentId;
}
