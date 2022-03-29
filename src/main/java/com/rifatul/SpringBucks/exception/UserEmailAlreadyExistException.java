package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserEmailAlreadyExistException extends ResponseStatusException {

    public UserEmailAlreadyExistException(String email) {
        super(HttpStatus.CONFLICT, String.format("The user email of %s already exists.", email));
    }

}