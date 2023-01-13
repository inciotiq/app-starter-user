package com.iotiq.user.exceptions;

import com.iotiq.commons.exceptions.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    protected UserNotFoundException(Object... args) {
        super("user", args);
    }
}
