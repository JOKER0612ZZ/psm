package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.Team;
import com.zz.psmback.common.entity.TeamMember;
import com.zz.psmback.common.entity.vo.TeamMemberView;
import com.zz.psmback.common.entity.vo.TeamView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.AuthenticateUtils;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.ProjectDao;
import com.zz.psmback.dao.TeamDao;
import com.zz.psmback.dao.TeamMemberDao;
import com.zz.psmback.service.TeamService;
import com.zz.psmback.service_utils.ProjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamDao teamDao;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    TeamMemberDao teamMemberDao;
    @Autowired
    ProjectUtils projectUtils;
    @Autowired
    AuthenticateUtils authenticateUtils;
    @Override
    public CommonResult<?> queryByUserId(int userId) {
        List<TeamView> list = null;
        try {
            list = teamDao.queryByUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
        return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), list);
    }


    @Override
    public CommonResult<?> updateTeam(TeamView view) {
        Team team = new Team(view);
        UpdateWrapper<Team> wrapper = new UpdateWrapper<>();
        wrapper.eq("team_Id", team.getTeamId());
        try {
            teamDao.update(team, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.UPDATE_ERROR.getCode(), ResponseCode.UPDATE_ERROR.getMessage(), null);
        }
        return CommonResult.success(ResponseCode.UPDATE_SUCCESS.getCode(), ResponseCode.UPDATE_SUCCESS.getMessage(), null);
    }

    @Override
    public CommonResult<?> queryTeamMembers(int teamId) {
        List<TeamMemberView> members;
        try {
            members = teamDao.queryByTeamId(teamId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), members);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
    }

    @Override
    public CommonResult<?> queryProjectMembers(int projectId) {
        List<TeamMemberView> members;
        try {
            members = teamDao.queryByProjectId(projectId);
            return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), members);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
    }

    @Override
    public CommonResult<?> joinTeam(int projectId, int userId) {
        LambdaQueryWrapper<Team> teamMemberQuery = new LambdaQueryWrapper<>();
        teamMemberQuery.eq(Team::getProjectId, projectId);
        try {
            Team team = teamDao.selectOne(teamMemberQuery);
            TeamMember teamMember = new TeamMember(team.getTeamId(), userId, 4);
            teamMemberDao.insert(teamMember);
            projectUtils.updateRedis(userId);
            LoginUser loginUser = authenticateUtils.refreshAuthentication();
            return CommonResult.success( ResponseCode.INSERT_SUCCESS.getCode(), ResponseCode.INSERT_SUCCESS.getMessage(), loginUser);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.INSERT_ERROR.getCode(), ResponseCode.INSERT_ERROR.getMessage(), null);
        }
    }


}
