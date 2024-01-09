package com.sofiaexport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class OrderAlreadyCompletedException extends RuntimeException {
    public OrderAlreadyCompletedException(String orderId) {
        super("Request for completion of order with id: " + orderId + " failed as order was already completed");
    }
}
