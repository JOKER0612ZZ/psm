package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zz.psmback.common.entity.Team;
import com.zz.psmback.common.entity.vo.TeamMember;
import com.zz.psmback.common.entity.vo.TeamView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.dao.TeamDao;
import com.zz.psmback.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamDao teamDao;
    @Override
    public CommonResult<?> queryByUserId(int userId) {
        List<TeamView> list =null;
        try{
            list = teamDao.queryByUserId(userId);

        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),ResponseCode.SELECT_ERROR.getMessage(),null);
        }
        return CommonResult.success(true,ResponseCode.SELECT_SUCCESS.getCode(),ResponseCode.SELECT_SUCCESS.getMessage(),list);
    }


    @Override
    public CommonResult<?> updateTeam(TeamView view) {
        Team team = new Team(view);
        UpdateWrapper<Team> wrapper = new UpdateWrapper<>();
        wrapper.eq("team_Id",team.getTeamId());
        try{
            teamDao.update(team,wrapper);
        }catch(Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.UPDATE_ERROR.getCode(),ResponseCode.UPDATE_ERROR.getMessage(),null);
        }
        return CommonResult.success(true,ResponseCode.UPDATE_SUCCESS.getCode(),ResponseCode.UPDATE_SUCCESS.getMessage(),null);
    }

    @Override
    public CommonResult<?> queryTeamMembers(int teamId) {
        List<TeamMember> members;
        try{
            members=teamDao.queryByTeamId(teamId);
            return CommonResult.success(true,ResponseCode.SELECT_SUCCESS.getCode(),ResponseCode.SELECT_SUCCESS.getMessage(),members);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(),ResponseCode.SELECT_ERROR.getMessage(),null);
        }
    }
}
