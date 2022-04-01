package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductAlreadyOnCartException extends ResponseStatusException {
    public ProductAlreadyOnCartException(long orderId, int productId) {
        super(HttpStatus.CONFLICT, "the cart #" + orderId + " is already contains the product #" + productId);
    }
}
