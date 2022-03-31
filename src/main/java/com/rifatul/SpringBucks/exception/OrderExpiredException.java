package com.rifatul.SpringBucks.exception;

import com.rifatul.SpringBucks.domain.model.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderExpiredException extends ResponseStatusException {


    public OrderExpiredException(OrderStatus status) {
        super(HttpStatus.BAD_REQUEST, "the order cannot be further modify as it is " + status);
    }
}
