package com.sofiaexport.exception;

public class PendingOrderNotFoundException extends ResourceNotFoundException {
    public PendingOrderNotFoundException(String userId) {
        super("Pending UserOrder was not found for user with id: " + userId);
    }
}
