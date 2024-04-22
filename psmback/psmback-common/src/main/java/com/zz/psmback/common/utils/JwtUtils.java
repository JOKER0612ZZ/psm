package com.zz.psmback.common.utils;

import com.zz.psmback.common.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
@Component
public class JwtUtils {
    private static final String header ="token";
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    private static final String SECRET ="d3f591959d4a4e28b260422c05b4418b";
    //生成jwtToken
    public static String getJwtToken(LoginUser user) {
        String JwtToken;
        JwtToken = Jwts.builder()
                .setSubject("zouzan")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("userId", user.getUserId())
                .claim("userName", user.getUserName())
                // 传入Key对象
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
        return JwtToken;
    }
    //解析jwtToken
    public static Jws<Claims> decode(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8))).build().parseClaimsJws(jwtToken);
    }
}
