package com.rifatul.SpringBucks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class IncorrectPriceFormatException extends ResponseStatusException {

    public IncorrectPriceFormatException(double price) {
        super(HttpStatus.EXPECTATION_FAILED, String.format("the price value is %d which is less or equal to 0", price));
    }

    public IncorrectPriceFormatException(String message) {
        super(HttpStatus.EXPECTATION_FAILED, message);
    }
}