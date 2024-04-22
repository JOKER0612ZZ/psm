package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.TeamMember;
import com.zz.psmback.common.entity.User;
import com.zz.psmback.common.entity.vo.ProjectView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.dao.ProjectDao;
import com.zz.psmback.dao.TeamMemberDao;
import com.zz.psmback.dao.UserDao;
import com.zz.psmback.service.TeamMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeamMemberServiceImpl implements TeamMemberService {
    @Autowired
    TeamMemberDao teamMemberDao;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserDao userDao;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ProjectDao projectDao;
    @Override
    public CommonResult<?> removeMember(int teamId, int memberId) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamId,teamId).eq(TeamMember::getMemberId,memberId);
        try{
            teamMemberDao.delete(wrapper);
            User user = userDao.selectById(memberId);
            LoginUser loginUser = (LoginUser) userDetailsService.loadUserByUsername(user.getUserName());
            redisUtils.set("UserDetails_"+loginUser.getUserId(),loginUser);
            List<ProjectView> projects = projectDao.queryUserProject(memberId);
            redisUtils.delete("Project_User_"+memberId);
            redisUtils.lPush("Project_User_"+memberId,projects);
            return CommonResult.success(ResponseCode.DELETE_SUCCESS.getCode(), ResponseCode.DELETE_SUCCESS.getMessage(),null);
        }catch(Exception e){
            log.error(e.getMessage());
            return CommonResult.error(ResponseCode.DELETE_ERROR.getCode(), ResponseCode.DELETE_ERROR.getMessage(), null);
        }
    }
}
