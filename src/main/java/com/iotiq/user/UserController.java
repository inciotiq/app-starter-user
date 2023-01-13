package com.iotiq.user;

import com.iotiq.commons.PagedResponse;
import com.iotiq.commons.PagedResponseBuilder;
import com.iotiq.user.domain.User;
import com.iotiq.user.messages.UserCreateDto;
import com.iotiq.user.messages.UserDto;
import com.iotiq.user.messages.UserFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
//    @PreAuthorize("hasAuthority(@UserManagementAuth.VIEW)")
    public PagedResponse<UserDto> getAll(UserFilter userFilter, Sort sort) {
        Page<User> userPage = userRepository.findAll(userFilter.buildSpecification(), userFilter.buildPageable(sort));
        List<UserDto> dtos = userPage.getContent().stream().map(UserDto::of).toList();

        return PagedResponseBuilder.createResponse(userPage, dtos);
    }

//    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority(@UserManagementAuth.VIEW) or #id.equals(principal.id)")
//    public ResponseEntity<UserDto> getOne(@PathVariable UUID id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityNames.User));
//        return ResponseEntity.ok(UserDto.of(user));
//    }

    @PostMapping
//    @PreAuthorize("hasAuthority(@UserManagementAuth.CREATE)")
    public UserDto create(@RequestBody @Valid UserCreateDto request) {
        User user = new User();
        userRepository.save(user);
        return UserDto.of(user);
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority(@UserManagementAuth.UPDATE) or #id.equals(principal.id)")
//    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody @Valid UserUpdateDto request) {
//        User user = userService.update(id, request);
//        return ResponseEntity.ok(UserDto.of(user));
//    }

//    @PutMapping("/{id}/password")
//    @PreAuthorize("hasAuthority(@UserManagementAuth.CHANGE_PASSWORD) or #id.equals(principal.id)")
//    public ResponseEntity<UserDto> updatePassword(@PathVariable UUID id, @RequestBody @Valid UpdatePasswordDto request) {
//        User user = userService.changePassword(id, request);
//        return ResponseEntity.ok(UserDto.of(user));
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority(@UserManagementAuth.DELETE) or #id.equals(principal.id)")
//    public void delete(@PathVariable UUID id) {
//        userRepository.deleteById(id);
//    }
}
