package com.zz.psmback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zz.psmback.common.entity.*;
import com.zz.psmback.common.entity.vo.ProjectView;
import com.zz.psmback.common.entity.vo.TeamMemberView;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.*;
import com.zz.psmback.dao.ProjectDao;
import com.zz.psmback.dao.PsmFileDao;
import com.zz.psmback.dao.TeamDao;
import com.zz.psmback.dao.TeamMemberDao;
import com.zz.psmback.service.ProjectService;
import com.zz.psmback.service_utils.ProjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 30代表项目
 * 0代表成功，1代表失败
 * 300x代表请求成功，301x代表请求失败
 **/
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectDao projectDao;
    @Autowired
    ProjectUtils projectUtils;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TeamMemberDao teamMemberDao;
    @Autowired
    PsmFileDao psmFileDao;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    FileUtils fileUtils;
    @Autowired
    @Qualifier("recordUtils")
    RecordUtils<ProjectView> recordUtils;
    @Autowired
    DateTimeFormatterUtils dateUtils;
    @Value("${file-storage.upload-dir}")
    private String dir;
    @Autowired
    AuthenticateUtils authenticationUtils;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> createProject(Project project, String teamName) {

        try {
            // 查询是否存在相同标记的项目
            QueryWrapper<Project> query = new QueryWrapper<>();
            query.eq("mark", project.getMark());
            Project existingProject = projectDao.selectOne(query);
            if (existingProject != null) {
                return CommonResult.error(3010, "项目标记已存在，请使用其他标记", null);
            }
            // 设置项目的截止日期和创建时间
            project.setDeadline(DateTimeFormatterUtils.format(project.getDeadline()));
            project.setCreationTime(String.valueOf(LocalDateTime.now()));
            // 插入项目记录
            int result = projectDao.insert(project);
            if (result == 0) {
                return CommonResult.error(3011, "创建项目失败", null);
            }
            System.out.println(project.getProjectId());
            // 插入项目团队
            Team team = new Team(teamName, project.getCreatorId(), String.valueOf(LocalDateTime.now()), project.getProjectId());
            int teamInsertResult = teamDao.insert(team);
            if (teamInsertResult == 0) {
                // 如果插入团队失败，则删除项目记录
                projectDao.deleteById(project.getProjectId());
                return CommonResult.error(3011, "创建项目失败", null);
            }
            int teamId = team.getTeamId();
            // 插入teamMember记录
            TeamMember teamMember =new TeamMember(teamId,project.getCreatorId(),3);
            int teamMemberInsertResult = teamMemberDao.insert(teamMember);
            if (teamMemberInsertResult == 0) {
                // 如果插入PSM文件失败，则删除项目记录和文件夹
                projectDao.deleteById(project.getProjectId());
                return CommonResult.error(3011, "创建项目失败", null);
            }
            // 创建项目相关的文件夹
            boolean folderCreated = fileUtils.createFolder(project.getMark());
            if (!folderCreated) {
                // 如果创建文件夹失败，则删除项目记录
                projectDao.deleteById(project.getProjectId());
                return CommonResult.error( 3011, "创建项目失败", null);
            }

            // 插入PSM文件记录
            PsmFile psmFile = new PsmFile(project.getMark(), project.getMark(), project.getCreatorId(),
                    String.valueOf(LocalDateTime.now()), project.getProjectId(), "folder", null);
            int psmFileInsertResult = psmFileDao.insert(psmFile);
            if (psmFileInsertResult == 0) {
                // 如果插入PSM文件失败，则删除项目记录和文件夹
                projectDao.deleteById(project.getProjectId());
                fileUtils.deleteFolder(project.getMark());
                return CommonResult.error(3011, "创建项目失败", null);
            }
            //更新认证
            LoginUser loginUser =  authenticationUtils.refreshAuthentication();
            if(loginUser == null){
                projectDao.deleteById(project.getProjectId());
                fileUtils.deleteFolder(project.getMark());
                return CommonResult.error(3011, "创建项目失败", null);
            }
            // 更新Redis
            projectUtils.updateRedis(project.getCreatorId());
            // 返回创建项目成功的结果
            return CommonResult.success(3001, "创建项目成功", loginUser);

        } catch (Exception e) {
            // 捕获异常，回滚事务并删除已创建的文件夹
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            fileUtils.deleteFolder(project.getMark());
            return CommonResult.error(3012, e.getMessage(), null);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public CommonResult<?> queryUserProject(Integer userId) {
        List<ProjectView> projects;
        if (redisUtils.hasKey("Project_User_" + userId)) {
            projects = (List<ProjectView>) redisUtils.Range("Project_User_" + userId);
            if(projects!=null){
                HashSet<ProjectView> set = new HashSet<>(projects);
                projects = new ArrayList<>(set);
            }
            log.info("从redis中取得:Project_User_" + userId);
        } else {
            try {
                projects = projectDao.queryUserProject(userId);
                log.info("从Mysql中取得:Project_User_" + userId);
                redisUtils.lPush("Project_User_" + userId, projects);
                redisUtils.expire("Project_User_" + userId, 60 * 60 * 24);
            } catch (Exception e) {
                return CommonResult.error( ResponseCode.SELECT_ERROR.getCode(), e.getMessage(), null);
            }
        }
        return CommonResult.success(ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), projects);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addRecord(ProjectView projectView, int userId) {
        RecordUtils<ProjectView> list = new RecordUtils<>(10);
        List<ProjectView> backup = null;
        if (redisUtils.hasKey("Record_" + userId)) {
            backup = (List<ProjectView>) redisUtils.get("Record_" + userId);
            if(backup!=null){
                LinkedList<ProjectView> a = new LinkedList<>(backup);
                list.setList(a);
            }
        }
        try {
            list.add(projectView);
            redisUtils.set("Record_" + userId, list.getList());
            redisUtils.expire("Record_" + userId,24*60*60*15);
        } catch (Exception e) {
            e.printStackTrace();
            redisUtils.set("Record_" + userId, backup);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommonResult<?> getRecords(int userId) {
        List<ProjectView> records = new LinkedList<>();
        List<ProjectView> backup = new LinkedList<>();
        if (redisUtils.hasKey("Record_" + userId)) {
            backup = (List<ProjectView>) redisUtils.get("Record_" + userId);
        }
        try {
            records = (List<ProjectView>) redisUtils.get("Record_" + userId);
        } catch (Exception e) {
            redisUtils.set("Record_" + userId, backup);
        }
        return CommonResult.success( ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), records);
    }

    @Override
    public CommonResult<?> queryProgress(int projectId) {
        Double percents;
        try {
            percents = projectDao.queryProgress(projectId);
            if (percents == null) percents = 0.0;
            percents = (Math.round(percents * 10.0) / 10.0);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(), null);
        }
        return CommonResult.success( ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), percents);
    }

    @Override
    public boolean createFolder(String path) {
        return fileUtils.createFolder(path);
    }

    private static class Pair{
        private Set<Integer> set;
        private Double percent;
        public Pair(){}
        public Pair(Set<Integer> set, Double percent) {
            this.set = set;
            this.percent = percent;
        }
        public Set<Integer> getSet() {
            return set;
        }
        public void setSet(Set<Integer> set) {
            this.set = set;
        }
        public Double getPercent() {
            return percent;
        }
        public void setPercent(Double percent) {
            this.percent = percent;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void active(Integer projectId, Integer userId) {
        List<TeamMemberView> members = teamDao.queryByProjectId(projectId);
        int total = members.size();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        String key = "active_" + projectId;
        Map<String, Pair> map = new LinkedHashMap<>();
        Pair pair;
        if (!redisUtils.hasKey(key)) {
            Set<Integer> activeSet = new HashSet<>();
            activeSet.add(userId);
            pair = new Pair(activeSet, (Math.round(activeSet.size() * 100.0) / total * 10.0) / 10.0);
            map.put(dateStr, pair);
            redisUtils.set(key, map);
        } else {
            map = (Map<String, Pair>) redisUtils.get(key);
            pair = map.getOrDefault(dateStr, new Pair(new HashSet<>(), 0.0));
            pair.getSet().add(userId);
            double percent = Math.round((pair.getSet().size() * 100.0) / total * 10.0)/10.0;
            pair.setPercent(percent);
            map.put(dateStr, pair);
            // 删除超过7天的记录
            if(map.size() > 7){
                String oldestKey = map.keySet().iterator().next();
                map.remove(oldestKey);
            }
            redisUtils.set(key, map);
            redisUtils.expire(key,24*60*60*7);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommonResult<?> getActive(Integer projectId) {
        String key ="active_"+projectId;
        Map<String,Pair> map;
        if (redisUtils.hasKey(key)) {
            map = (Map<String, Pair>) redisUtils.get(key);
            return CommonResult.success( ResponseCode.SELECT_SUCCESS.getCode(), ResponseCode.SELECT_SUCCESS.getMessage(), map);
        }
        return CommonResult.error(ResponseCode.SELECT_ERROR.getCode(), ResponseCode.SELECT_ERROR.getMessage(),null);
    }

    @Override
    public CommonResult<?> updateProject(Integer projectId,Project project) {
        LambdaUpdateWrapper<Project> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Project::getProjectId, projectId);
        try{
            project.setCreationTime(DateTimeFormatterUtils.format(project.getCreationTime()));
            project.setDeadline(DateTimeFormatterUtils.format(project.getDeadline()));
            redisUtils.delete("Project_User_"+project.getCreatorId());
            projectDao.update(project,wrapper);
            return CommonResult.success(ResponseCode.UPDATE_SUCCESS.getCode(), ResponseCode.UPDATE_SUCCESS.getMessage(), null);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(ResponseCode.UPDATE_ERROR.getCode(), ResponseCode.UPDATE_ERROR.getMessage(),null);
        }
    }
}

