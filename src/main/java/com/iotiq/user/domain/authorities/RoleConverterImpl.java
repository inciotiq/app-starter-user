package com.iotiq.user.domain.authorities;

import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter {
    @Override
    public Role convert(String role) {
        if (role.equals("ADMIN")) {
            return BaseRole.ADMIN;
        }
        return null;
    }
}
