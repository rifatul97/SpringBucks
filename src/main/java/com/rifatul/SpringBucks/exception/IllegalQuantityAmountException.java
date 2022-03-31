package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IllegalQuantityAmountException extends ResponseStatusException  {
    public IllegalQuantityAmountException() {
        super(HttpStatus.BAD_REQUEST, "the quantity value cannot be less or equal to zero");
    }
}
