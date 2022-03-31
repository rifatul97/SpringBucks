package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderNotExistingException extends ResponseStatusException {
    public OrderNotExistingException(long orderId) {
        super(HttpStatus.NOT_FOUND, "the order #" + orderId + " cannot be found");
    }
}
