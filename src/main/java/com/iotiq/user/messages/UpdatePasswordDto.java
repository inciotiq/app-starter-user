package com.iotiq.user.messages;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
