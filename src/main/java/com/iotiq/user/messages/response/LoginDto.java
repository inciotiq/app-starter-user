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
    Set<String> authorities;
    String idToken;
    String refreshToken;
}
