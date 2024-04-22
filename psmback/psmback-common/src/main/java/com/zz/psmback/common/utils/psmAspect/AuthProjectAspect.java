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
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @author zouzan
 * @date 2024/03/14
 */
@Slf4j
@Aspect
@Component
public class AuthProjectAspect {
    @Autowired
    RedisUtils redisUtils;


    /**
     * 功能描述：对项目中的操作进行鉴权
     *
     * @param joinPoint
     * @param authProject
     * @return {@code Object }
     * @author zouzan
     * @date 2024/03/14
     */
    @Around("@annotation(authProject)")
    public Object authPermission(ProceedingJoinPoint joinPoint, AuthProject authProject) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        int projectId = 0;
        for (int i = 0; i < parameterNames.length; i++) {
            if ("projectId".equals(parameterNames[i])) {
                projectId = (Integer) args[i];
                break;
            }
        }

        String permission = authProject.value();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int finalProjectId = projectId;
        Optional<ProjectAuthorities> projectAuthority = loginUser.getProjectAuthorities().stream()
                .filter(pa -> pa.getProjectId() == finalProjectId)
                .findFirst();

        if (projectAuthority.isPresent() && hasPermission(permission, projectAuthority.get())) {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        return CommonResult.error( 403, "没有权限", null);
    }

    /**
     * @param permission
     * @param projectAuthority
     * @return boolean
     */
    public boolean hasPermission(String permission, ProjectAuthorities projectAuthority) {
        return projectAuthority != null &&
                projectAuthority.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthorityName().equals(permission));
    }
}
