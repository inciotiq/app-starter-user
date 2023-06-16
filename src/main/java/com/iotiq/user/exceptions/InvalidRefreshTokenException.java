package com.iotiq.user.exceptions;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class InvalidRefreshTokenException extends ApplicationException {
    public InvalidRefreshTokenException() {
        super(HttpStatus.BAD_REQUEST, "invalidRefreshToken", Collections.emptyList());
    }
}
