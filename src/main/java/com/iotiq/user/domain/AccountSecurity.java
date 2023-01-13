package com.iotiq.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
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
    private Role role;

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}
