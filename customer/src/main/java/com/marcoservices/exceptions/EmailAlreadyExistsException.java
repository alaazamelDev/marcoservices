package com.marcoservices.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Email already exists";
    }

    public EmailAlreadyExistsException() {
        super("Email already exists");
    }
}
