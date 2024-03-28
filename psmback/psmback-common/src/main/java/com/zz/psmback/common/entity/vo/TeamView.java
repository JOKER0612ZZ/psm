package com.zz.psmback.common.entity.vo;

import com.zz.psmback.common.entity.Team;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeamView extends Team {
    private String creatorName;
}
