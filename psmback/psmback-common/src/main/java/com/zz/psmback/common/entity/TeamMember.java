package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zz.psmback.common.entity.vo.TeamMemberView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("team_member")
public class TeamMember {
    private Integer teamId;
    private Integer memberId;
    private Integer roleId;
    public TeamMember(){}
    public TeamMember(TeamMemberView teamMemberView){
        this.teamId = teamMemberView.getTeamId();
        this.memberId = teamMemberView.getMemberId();
        this.roleId = teamMemberView.getRoleId();
    }
    public TeamMember(int teamId, int memberId, int roleId){
        this.teamId = teamId;
        this.memberId = memberId;
        this.roleId = roleId;
    }
}
