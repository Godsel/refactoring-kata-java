package com.sipios.refactoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnexpectedBodyException extends ResponseStatusException {
    public UnexpectedBodyException(HttpStatus status) {
        super(status);
    }
}
