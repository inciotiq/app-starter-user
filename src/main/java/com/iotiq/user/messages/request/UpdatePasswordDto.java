package com.iotiq.user.messages.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
