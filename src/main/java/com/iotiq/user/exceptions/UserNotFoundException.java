package com.iotiq.user.exceptions;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "userNotFound", Collections.emptyList());
    }
}
