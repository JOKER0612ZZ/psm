package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("task")
public class Task {
    private Integer taskId;
    private Integer projectId;
    private String title;
    private String description;
    private String status;
    private String creationTime;
    private String deadline;
    private Integer creatorId;
}
