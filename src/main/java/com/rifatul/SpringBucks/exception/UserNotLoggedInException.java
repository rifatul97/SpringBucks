package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotLoggedInException extends ResponseStatusException {

    public UserNotLoggedInException() {
        super(HttpStatus.BAD_REQUEST, String.format("There must be user logged in to make this request"));
    }

}