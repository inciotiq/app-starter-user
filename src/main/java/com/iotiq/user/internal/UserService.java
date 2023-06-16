package com.iotiq.user.internal;

import com.iotiq.commons.domain.AbstractMapper;
import com.iotiq.commons.exceptions.RequiredFieldMissingException;
import com.iotiq.commons.util.PasswordUtil;
import com.iotiq.user.domain.User;
import com.iotiq.user.exceptions.UserNotFoundException;
import com.iotiq.user.messages.request.UpdatePasswordDto;
import com.iotiq.user.messages.request.UserCreateDto;
import com.iotiq.user.messages.request.UserFilter;
import com.iotiq.user.messages.request.UserUpdateDto;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ExpressionMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.iotiq.commons.util.NullHandlerUtil.setIfNotNull;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

    private final UserMapper userMapper = new UserMapper();
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public Page<User> findAll(UserFilter userFilter, Sort sort) {
        return userRepository.findAll(userFilter.buildSpecification(), userFilter.buildPageable(sort));
    }

    public User find(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User find(String username) {
        return userRepository.findByAccountInfoUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public User create(UserCreateDto request) {
        User user = new User();

        userMapper.map(request, user);
        setIfNotNull(user::setPassword, () -> passwordUtil.encode(request.getPassword()), request.getPassword());
        setIfNotNull(user::setRole, request::getRole);
        setIfNotNull(user::setUsername, request::getUsername);
        setIfNotNull(s -> user.getPersonalInfo().setFirstName(s), request::getFirstname);
        setIfNotNull(s -> user.getPersonalInfo().setLastName(s), request::getLastname);
        setIfNotNull(s -> user.getPersonalInfo().setEmail(s), request::getEmail);

        return userRepository.save(user);
    }

    @Transactional
    public User update(UUID id, UserUpdateDto request) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        user.setUsername(request.getUsername());
        user.getPersonalInfo().setFirstName(request.getFirstname());
        user.getPersonalInfo().setLastName(request.getLastname());
        user.getPersonalInfo().setEmail(request.getEmail());
        user.setRole(request.getRole());

        return user;
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User changePassword(UUID id, UpdatePasswordDto request) {
        if (request.getNewPassword() == null) {
            throw new RequiredFieldMissingException("newPassword");
        }
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // fixme uncomment
//        if (principal.getAuthorities().contains(UserManagementAuthority.CHANGE_PASSWORD)) {
//            setPassword(user, request.getNewPassword());
//        } else if (Objects.equals(principal.getId(), user.getId())) {
//            updatePassword(user, request);
//        } else {
//            throw new InvalidCredentialException();
//        }
        userRepository.save(user); // 🥱

        return user;
    }

    private void updateIfAllowed(UserUpdateDto request, User user) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // fixme uncomment
//        if (principal.getAuthorities().contains(UserManagementAuthority.UPDATE)) {
//            user.setUsername(request.getUsername());
//            user.setRole(request.getRole());
//        }
    }

    private void updatePassword(User user, UpdatePasswordDto request) {
        if (StringUtils.isBlank(request.getOldPassword())) {
            throw new RequiredFieldMissingException("oldPassword");
        }
        // fixme uncomment
//        boolean matches = passwordEncoder.matches(request.getOldPassword(), user.getPassword());
//        if (matches) {
//            setPassword(user, request.getNewPassword());
//        } else {
//            throw new InvalidCredentialException();
//        }
    }

    private void setPassword(User user, String newPassword) {
        user.setPassword(passwordUtil.encode(newPassword));
    }

    protected static class UserMapper extends AbstractMapper<UserUpdateDto, User> {

        protected UserMapper() {
            super(UserUpdateDto.class, User.class);
        }

        @Override
        protected ExpressionMap<UserUpdateDto, User> getMappings() {
            return mapping -> {
                mapping.skip(User::setPassword);
                mapping.skip(User::setRole);
                mapping.skip(User::setUsername);
            };
        }
    }

}
