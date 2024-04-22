package com.zz.psmback.web.controller;


import com.zz.psmback.common.entity.vo.TeamView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.utils.psmAnnotation.AuthProject;
import com.zz.psmback.service.TeamMemberService;
import com.zz.psmback.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    TeamMemberService teamMemberService;
    @RequestMapping(value = "/queryByUserId/{userId}", method = RequestMethod.GET)
    public CommonResult<?> queryByUserId(@PathVariable("userId") int userId) {
        return teamService.queryByUserId(userId);
    }

    @RequestMapping(value = "/updateTeam", method = RequestMethod.POST)
    public CommonResult<?> updateTeam(@RequestBody TeamView view) {
        return teamService.updateTeam(view);
    }

    @RequestMapping(value = "/queryTeamMembers/{teamId}", method = RequestMethod.GET)
    public CommonResult<?> queryTeamMembers(@PathVariable("teamId") int teamId) {
        return teamService.queryTeamMembers(teamId);
    }

    @RequestMapping(value = "/queryProjectMembers/{projectId}", method = RequestMethod.GET)
    public CommonResult<?> queryProjectMembers(@PathVariable("projectId") int projectId) {
        return teamService.queryProjectMembers(projectId);
    }

    @RequestMapping(value = "/joinTeam/{projectId}/{userId}", method = RequestMethod.PUT)
    public CommonResult<?> joinTeam(@PathVariable("projectId") int projectId, @PathVariable("userId") int userId) {
        return teamService.joinTeam(projectId, userId);
    }

    @RequestMapping(value = "/removeMember/{teamId}/{memberId}", method = RequestMethod.DELETE)
    public CommonResult<?> removeMember(@PathVariable("teamId") int teamId, @PathVariable("memberId") int memberId){
        return teamMemberService.removeMember(teamId, memberId);
    }
}
