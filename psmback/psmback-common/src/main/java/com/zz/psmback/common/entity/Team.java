package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zz.psmback.common.entity.vo.TeamView;
import lombok.Data;

@Data
@TableName("team")
public class Team {
    private Integer teamId;
    private String teamName;
    private Integer creatorId;
    private String creationTime;
    private Integer projectId;
    public Team() {}
    public Team(String teamName, Integer creatorId, String creationTime, Integer projectId) {
        this.teamName = teamName;
        this.creatorId = creatorId;
        this.creationTime = creationTime;
        this.projectId = projectId;
    }
    public Team(TeamView teamView){
        this.teamId = teamView.getTeamId();
        this.teamName = teamView.getTeamName();
        this.creatorId = teamView.getCreatorId();
        this.creationTime = teamView.getCreationTime();
        this.projectId = teamView.getProjectId();
    }
}
