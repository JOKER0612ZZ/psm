package com.zz.psmback.common.utils.psmAspect;

import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.entity.vo.ProjectAuthorities;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.utils.RedisUtils;
import com.zz.psmback.common.utils.psmAnnotation.AuthProject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Aspect
@Component
public class AuthProjectAspect {
    @Autowired
    RedisUtils redisUtils;

    @Around("@annotation(authProject)")
    public Object authPermission(ProceedingJoinPoint joinPoint, AuthProject authProject) {
        Object[] args = joinPoint.getArgs();
        int projectId = (int) args[0];
        String permission = authProject.value();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Optional<ProjectAuthorities> projectAuthority = loginUser.getProjectAuthorities().stream()
                .filter(pa -> pa.getProjectId() == projectId)
                .findFirst();

        if (projectAuthority.isPresent() && hasPermission(permission, projectAuthority.get())) {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        return CommonResult.error(false, 403, "没有权限", null);
    }

    public boolean hasPermission(String permission, ProjectAuthorities projectAuthority) {
        return projectAuthority != null &&
                projectAuthority.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthorityName().equals(permission));
    }
}
