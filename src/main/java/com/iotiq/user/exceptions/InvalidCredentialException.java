package com.iotiq.user.exceptions;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class InvalidCredentialException extends ApplicationException {
    public InvalidCredentialException() {
        super(HttpStatus.FORBIDDEN, InvalidCredentialException.class.getSimpleName(), Collections.emptyList(), new Object[]{});
    }
}
