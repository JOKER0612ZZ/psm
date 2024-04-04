package com.zz.psmback.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zz.psmback.common.entity.LoginUser;
import com.zz.psmback.common.result.CommonResult;
import com.zz.psmback.common.result.ResponseCode;
import com.zz.psmback.common.utils.JwtUtils;
import com.zz.psmback.common.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zouzan
 * @date 2024/03/02
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    RedisUtils redisUtils;

    /**
     * 功能描述：过滤请求和登录状态校验
     *
     * @param request
     * @param response
     * @param filterChain
     * @author zouzan
     * @date 2024/02/20
     */

    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest request,
                                    javax.servlet.http.HttpServletResponse response,
                                    javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        //请求头中没有token就交给security去过滤
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        Claims claim = null;
        try{
            claim = JwtUtils.decode(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            writeJson(response,ResponseCode.TOKEN_VALIDATE_FAILED,HttpServletResponse.SC_OK,null,false);
            return;
        }
        assert claim != null;
        String username = (String) claim.get("userName");
        String redisToken = (String) redisUtils.get("Token_" + username);
        log.info("redisToken与token是否匹配:"+redisToken.equals(token));
        if (token.equals(redisToken)) {
            LoginUser loginUser = (LoginUser) redisUtils.get("UserDetails_" + username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }else {
            writeJson(response,ResponseCode.LOGIN_OUTDATED,HttpServletResponse.SC_OK,null,false);
            return;
        }
        filterChain.doFilter(request, response);
    }

    public static void writeJson(HttpServletResponse response,ResponseCode responseCode,int status,
                                 Object data,boolean isSuccess) throws IOException {
        CommonResult<Object> result = new CommonResult<>(isSuccess, responseCode.getCode(), responseCode.getMessage(), data);
        response.setStatus(status);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
