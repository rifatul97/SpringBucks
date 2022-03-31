package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductIsInUserCartException extends ResponseStatusException {
    public ProductIsInUserCartException(int productId) {
        super(HttpStatus.CONFLICT, "the product " + productId + "is on a user's cart");
    }
}
