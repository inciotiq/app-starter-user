package com.iotiq.user.security.jwt;

import lombok.Data;

@Data
public class TokenExpireProperties {
    int expirationMinutes;
}
