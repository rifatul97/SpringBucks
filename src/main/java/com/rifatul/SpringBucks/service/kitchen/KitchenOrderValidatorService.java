package com.rifatul.SpringBucks.service.kitchen;

import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class KitchenOrderValidatorService {
    public static void throwExceptionIfOrderIsNotYetOrdered(int orderId, OrderDao orderDao) {
        Order order = orderDao.selectById(orderId).get();
        if (!order.orderStatus().equals(OrderStatus.ONQUEUE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the order #"+ orderId + " is not ordered yet");
        }
    }

    public static void throwExceptionIfOrderNotStartToProcessYet(int orderId, OrderDao orderDao) {
        Order order = orderDao.selectById(orderId).get();
        if (!order.orderStatus().equals(OrderStatus.FULFILLING)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the order #"+ orderId + " is not started yet");
        }
    }
}
