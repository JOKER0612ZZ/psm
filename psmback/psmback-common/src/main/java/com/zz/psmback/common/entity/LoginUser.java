package com.zz.psmback.common.entity;

import com.zz.psmback.common.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUser extends User implements UserDetails {
    private String token;
    private String loginTime;
    private String expireTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return "LoginUser{" + super.toString() +
                "token='" + token + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", expireTime='" + expireTime + '\'' +
                "} " + super.toString();
    }
}

