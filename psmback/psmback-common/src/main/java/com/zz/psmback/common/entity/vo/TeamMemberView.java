package com.zz.psmback.common.entity.vo;

import com.zz.psmback.common.entity.TeamMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeamMemberView extends TeamMember {
    private String roleName;
    private String memberName;
    @Override
    public String toString() {
        return "TeamMemberView{" +
                "teamId=" + super.getTeamId() +
                ", memberId=" + super.getMemberId() +
                ", roleId=" + super.getRoleId() +
                ", roleName='" + roleName + '\'' +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
