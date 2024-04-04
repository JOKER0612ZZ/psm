package com.zz.psmback.web.controller;


import com.zz.psmback.common.entity.vo.TeamView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamService teamService;

    @RequestMapping(value = "/queryByUserId/{userId}", method = RequestMethod.GET)
    public CommonResult<?> queryByUserId(@PathVariable("userId") int userId) {
        return teamService.queryByUserId(userId);
    }

    @RequestMapping(value = "/updateTeam",method = RequestMethod.POST)
    public CommonResult<?> updateTeam(@RequestBody TeamView view) {
        return teamService.updateTeam(view);
    }

    @RequestMapping(value = "/queryTeamMembers/{teamId}",method = RequestMethod.GET)
    public CommonResult<?> queryTeamMembers(@PathVariable("teamId") int teamId){
        return teamService.queryTeamMembers(teamId);
    }
}
