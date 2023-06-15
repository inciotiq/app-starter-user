package com.iotiq.user.domain.authorities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("UserManagementAuth")
public class UserManagementAuthority {

    private UserManagementAuthority() {
    }

    public static final GrantedAuthority VIEW = new SimpleGrantedAuthority("USER_MANAGEMENT_VIEW");
    public static final GrantedAuthority CREATE = new SimpleGrantedAuthority("USER_MANAGEMENT_CREATE");
    public static final GrantedAuthority UPDATE = new SimpleGrantedAuthority("USER_MANAGEMENT_UPDATE");
    public static final GrantedAuthority DELETE = new SimpleGrantedAuthority("USER_MANAGEMENT_DELETE");
    public static final GrantedAuthority CHANGE_PASSWORD = new SimpleGrantedAuthority("USER_MANAGEMENT_CHANGE_PASSWORD");
}
