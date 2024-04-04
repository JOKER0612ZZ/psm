package com.zz.psmback.service;

import com.zz.psmback.common.entity.vo.TeamView;
import com.zz.psmback.common.result.CommonResult;

public interface TeamService {
    CommonResult<?> queryByUserId(int userId);
    CommonResult<?> updateTeam(TeamView view);
    CommonResult<?> queryTeamMembers(int teamId);
}
