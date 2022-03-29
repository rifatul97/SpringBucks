package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

public class CategoryDoesNotExistException extends ResponseStatusException {

    public CategoryDoesNotExistException(int categoryId) {
        super(HttpStatus.NOT_FOUND, "the category id# " + categoryId + " is not found in the database");
    }

}
