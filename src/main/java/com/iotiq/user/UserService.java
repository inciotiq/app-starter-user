package com.iotiq.user;

import com.iotiq.user.domain.User;
import com.iotiq.user.messages.UpdatePasswordDto;
import com.iotiq.user.messages.UserCreateDto;
import com.iotiq.user.messages.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    public Page<User> findAll(Specification<User> buildSpecification, Pageable buildPageable) {
        return null;
    }

    public User find(UUID id) {
        return null;
    }

    public User create(UserCreateDto request) {
        return null;
    }

    public User update(UUID id, UserUpdateDto request) {
        return null;
    }

    public User changePassword(UUID id, UpdatePasswordDto request) {
        return null;
    }

    public void delete(UUID id) {

    }

}
