package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Objects;

@Data
@TableName("project")
public class Project {
    @TableId(type = IdType.AUTO)
    private Integer projectId;
    private String projectName;
    private String projectStatus;
    private String description;
    private String creationTime;
    private String deadline;
    private Integer creatorId;
    private String mark;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return getProjectId().equals(project.getProjectId()) && getMark().equals(project.getMark());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(), getMark());
    }
}
