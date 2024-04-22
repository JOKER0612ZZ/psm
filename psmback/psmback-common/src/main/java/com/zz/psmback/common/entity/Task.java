package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zz.psmback.common.entity.vo.TaskView;
import lombok.Data;

@Data
@TableName("task")
public class Task {
    @TableId(type = IdType.AUTO)
    private Integer taskId;
    private Integer projectId;
    private String title;
    private String description;
    private String status;
    private String creationTime;
    private String deadline;
    private Integer creatorId;

    public Task(){

    }
    public Task(TaskView taskView){
        this.taskId = taskView.getTaskId();
        this.projectId = taskView.getProjectId();
        this.title = taskView.getTitle();
        this.description = taskView.getDescription();
        this.status = taskView.getStatus();
        this.creationTime = taskView.getCreationTime();
        this.deadline = taskView.getDeadline();
        this.creatorId = taskView.getCreatorId();
    }
}
