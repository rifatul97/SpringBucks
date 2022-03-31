package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

public class CartItemNotExistingException extends ResponseStatusException {
    public CartItemNotExistingException(int cartItemId) {
        super(HttpStatus.NOT_FOUND, "the cart item #" + cartItemId + " is not created" );
    }
}
