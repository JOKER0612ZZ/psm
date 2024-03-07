package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("team")
public class Team {
    private Integer teamId;
    private String teamName;
    private Integer creatorId;
    private String creationTime;
    private Integer projectId;
}
