package com.zz.psmback.common.entity;

import com.zz.psmback.common.entity.vo.ProjectAuthorities;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUser extends User implements UserDetails {
    private String token;
    private String loginTime;
    private String expireTime;
    private List<SimpleGrantedAuthority> authorities;
    private List<ProjectAuthorities> projectAuthorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return super.getPassword();
    }
    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "token='" + token + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", authorities=" + authorities +
                ", projectAuthorities=" + projectAuthorities +
                '}'+super.toString();
    }
}

