package com.zz.psmback.common.entity.vo;

import lombok.Data;

@Data
public class TeamMember {
    private int teamId;
    private int memberId;
    private int roleId;
    private String roleName;
    private String memberName;

    @Override
    public String toString() {
        return "TeamMember{" +
                "teamId=" + teamId +
                ", memberId=" + memberId +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
