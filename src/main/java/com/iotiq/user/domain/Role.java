package com.iotiq.user.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {
    ADMIN(
            new SimpleGrantedAuthority("ROLE_ADMIN")
    );

    private final List<GrantedAuthority> authorities;

    Role(GrantedAuthority... authorities) {
        this.authorities = List.of(authorities);
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}