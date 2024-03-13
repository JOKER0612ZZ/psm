package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("project")
public class Project {
    private Integer projectId;
    private String projectName;
    private String projectStatus;
    private String description;
    private String creationTime;
    private String deadline;
    private Integer creatorId;
    private String mark;
}
