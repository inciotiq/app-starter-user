package com.iotiq.user.security;

import com.iotiq.user.domain.User;
import com.iotiq.user.exceptions.InvalidRefreshTokenException;
import com.iotiq.user.internal.UserService;
import com.iotiq.user.messages.request.LoginRequest;
import com.iotiq.user.messages.request.RefreshTokenRequest;
import com.iotiq.user.messages.response.LoginDto;
import com.iotiq.user.security.jwt.JWTFilter;
import com.iotiq.user.security.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Tag(name = "Authentication", description = "Authentication API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserService userService;

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public LoginDto authorize(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createAccessToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        String refreshToken = null;
        if (loginRequest.isRememberMe()) {
            refreshToken = tokenProvider.createRefreshToken(authentication);
        }

        User user = userService.find(loginRequest.getUsername());
        return LoginDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .authorities(user.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet()))
                .idToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @PostMapping("/refreshToken")
    public LoginDto refresh(@Valid @RequestBody RefreshTokenRequest request) {
        if (!tokenProvider.validateToken(request.getRefreshToken())) throw new InvalidRefreshTokenException();

        var authenticationToken = tokenProvider.getAuthentication(request.getRefreshToken());
        User user = userService.find(getUserName(authenticationToken));

        var authorities = user.getAuthorities();
        String accessToken = tokenProvider.createAccessToken(createPreAuthToken(authenticationToken, authorities));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        return LoginDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .authorities(user.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet()))
                .idToken(accessToken)
                .build();
    }

    private String getUserName(Authentication authenticationToken) {
        return ((User) authenticationToken.getPrincipal()).getUsername();
    }

    private <T extends GrantedAuthority> PreAuthenticatedAuthenticationToken createPreAuthToken(
            Authentication authenticationToken, Collection<T> authorities) {
        return new PreAuthenticatedAuthenticationToken(authenticationToken.getPrincipal(),
                authenticationToken.getCredentials(), authorities);
    }
}
