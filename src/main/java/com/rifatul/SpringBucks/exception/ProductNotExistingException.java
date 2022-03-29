package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotExistingException extends ResponseStatusException {

    public ProductNotExistingException(int productId) {
        super(HttpStatus.NOT_FOUND, String.format("The product of # %d does not exist in the database", productId));
    }

}