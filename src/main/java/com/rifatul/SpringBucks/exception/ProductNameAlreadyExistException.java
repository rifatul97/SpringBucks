package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ProductNameAlreadyExistException  extends ResponseStatusException {

    public ProductNameAlreadyExistException(String name) {
        super(HttpStatus.CONFLICT, String.format("The product name of %s has been already taken", name));
    }

}