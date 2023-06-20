package com.iotiq.user.domain;

import com.iotiq.user.domain.authorities.Role;
import com.iotiq.user.domain.authorities.RoleColumnConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Data
@Embeddable
public class AccountSecurity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1905122041950251207L;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean enabled;
    @Convert(converter = RoleColumnConverter.class)
    private Role role;

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}
