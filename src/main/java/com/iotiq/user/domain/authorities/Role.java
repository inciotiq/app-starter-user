package com.iotiq.user.domain.authorities;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public interface Role extends Serializable {
    List<GrantedAuthority> getAuthorities();

    String name();
}