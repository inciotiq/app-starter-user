package com.iotiq.user;

import com.iotiq.commons.message.response.PagedResponse;
import com.iotiq.commons.message.response.PagedResponseBuilder;
import com.iotiq.commons.util.HeaderUtil;
import com.iotiq.user.domain.User;
import com.iotiq.user.internal.UserService;
import com.iotiq.user.messages.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${application.name}")
    private String applicationName;

    @GetMapping
//    @PreAuthorize("hasAuthority(@UserManagementAuth.VIEW)")
    public PagedResponse<UserDto> getAll(UserFilter userFilter, Sort sort) {
        Page<User> userPage = userService.findAll(userFilter, sort);
        List<UserDto> dtos = userPage.getContent().stream().map(UserDto::of).toList();

        return PagedResponseBuilder.createResponse(userPage, dtos);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority(@UserManagementAuth.VIEW) or #id.equals(principal.id)")
    public UserDto getOne(@PathVariable UUID id) {
        User user = userService.find(id);
        return UserDto.of(user);
    }

    /**
     * {@code POST  /users}  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user, or with status {@code 400 (Bad Request)} if the login or email is already in use.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or email is already in use.
     */
    @PostMapping
//    @PreAuthorize("hasAuthority(@UserManagementAuth.CREATE)")
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserCreateDto request) throws URISyntaxException {
        User user = userService.create(request);
        return ResponseEntity
                .created(new URI("/api/v1/users/" + user.getId()))
                .headers(HeaderUtil.createAlert(applicationName, "userManagement.created", String.valueOf(user.getId())))
                .body(UserDto.of(user));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority(@UserManagementAuth.UPDATE) or #id.equals(principal.id)")
    public UserDto update(@PathVariable UUID id, @RequestBody @Valid UserUpdateDto request) {
        User user = userService.update(id, request);
        return UserDto.of(user);
    }

    @PutMapping("/{id}/password")
//    @PreAuthorize("hasAuthority(@UserManagementAuth.CHANGE_PASSWORD) or #id.equals(principal.id)")
    public UserDto updatePassword(@PathVariable UUID id, @RequestBody @Valid UpdatePasswordDto request) {
        User user = userService.changePassword(id, request);
        return UserDto.of(user);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority(@UserManagementAuth.DELETE) or #id.equals(principal.id)")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
