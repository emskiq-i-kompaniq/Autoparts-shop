package com.sofiaexport.exception;


public class AutoPartNotFoundException extends ResourceNotFoundException {
    public AutoPartNotFoundException(Long id) {
        super("AutoPart with the following id: " + id + " was not found");
    }
}
