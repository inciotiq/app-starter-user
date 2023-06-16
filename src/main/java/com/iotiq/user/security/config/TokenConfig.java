package com.iotiq.user.security.config;

import com.iotiq.user.security.jwt.TokenExpireProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app.jwt")
@Data
@Configuration
public class TokenConfig {
    String secret;
    TokenExpireProperties refresh;
    TokenExpireProperties access;

}
