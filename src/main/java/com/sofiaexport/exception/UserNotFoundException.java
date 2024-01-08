package com.sofiaexport.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(Long id) {
        super("User with the following id: " + id + " was not found");
    }
}
