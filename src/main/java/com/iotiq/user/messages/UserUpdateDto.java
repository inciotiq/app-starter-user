package com.iotiq.user.messages;

import com.iotiq.user.domain.authorities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateDto {
    @NotEmpty
    private String username;
    @NotNull
    private Role role;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    @Email
    private String email;
}
