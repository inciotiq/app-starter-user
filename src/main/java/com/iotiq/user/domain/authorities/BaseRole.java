package com.iotiq.user.domain.authorities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum BaseRole implements Role {

    ADMIN(
            new SimpleGrantedAuthority("ROLE_ADMIN"),

            UserManagementAuthority.VIEW,
            UserManagementAuthority.CREATE,
            UserManagementAuthority.UPDATE,
            UserManagementAuthority.DELETE,
            UserManagementAuthority.CHANGE_PASSWORD
    );

    final List<GrantedAuthority> authorities;

    BaseRole(GrantedAuthority... authorities) {
        this.authorities = List.of(authorities);
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}