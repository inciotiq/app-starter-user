package com.iotiq.user.messages.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    UUID id;
    String username;
    Set<String> roles;
    String idToken;
    String refreshToken;
}
