package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.exception.OrderNotExistingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderValidatorService {

    public static void throwExceptionIfOrderStatusIsWrong(int orderId, OrderDao orderDao) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid order status request");
    }

    public static void throwExceptionIfUserOrderDoesNotExist(long orderId, OrderDao orderDao) {
        if (orderDao.selectById(orderId).isEmpty()) {
            throw new OrderNotExistingException(orderId);
        }
    }


    public static void throwExceptionIfUserOrderIsNotNew(int orderId, OrderDao orderDao) {
        //throw new NotImplementedException();
    }

}
