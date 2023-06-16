package com.iotiq.user.messages.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "validation.refreshToken.null")
    private String refreshToken;
}
