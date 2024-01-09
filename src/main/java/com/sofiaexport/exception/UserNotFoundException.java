package com.sofiaexport.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String id) {
        super("User with the following id: " + id + " was not found");
    }
}
