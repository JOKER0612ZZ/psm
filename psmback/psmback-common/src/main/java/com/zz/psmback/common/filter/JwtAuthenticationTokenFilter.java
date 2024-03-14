package com.zz.psmback.common.filter;

import com.zz.psmback.common.entity.LoginUser;
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
        Claims claim = JwtUtils.decode(token).getBody();
        String username = (String) claim.get("userName");
        String redisToken = (String) redisUtils.get("Token_" + username);
        log.info("redisToken与token是否匹配:"+redisToken.equals(token));
        if (token.equals(redisToken)) {
            LoginUser loginUser = (LoginUser) redisUtils.get("UserDetails_" + username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }else {
            throw new RuntimeException("用户未登录");
        }
        filterChain.doFilter(request, response);
    }
}
