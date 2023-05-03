package com.rifatul.SpringBucks.service.order;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import com.rifatul.SpringBucks.exception.OrderNotExistingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class OrderValidatorService {

    public static void throwExceptionIfWrongOrderStatusProceesed(int orderId, OrderStatus requestedOrder, OrderDao orderDao) {
        OrderStatus orderStatus = orderDao.selectById(orderId).get().orderStatus();
        if (!orderStatus.getNextStep().equals(requestedOrder)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid order status request");
        }
    }

    public static void throwExceptionIfUserOrderDoesNotExist(long orderId, OrderDao orderDao) {
        if (orderDao.selectById(orderId).isEmpty()) {
            throw new OrderNotExistingException(orderId);
        }
    }

    public static void throwExceptionIfUserOrderIsNotNew(int orderId, OrderDao orderDao) {
        Order order = orderDao.selectById(orderId).get();
        OrderStatus orderStatus = order.orderStatus();
        if (!orderStatus.equals(OrderStatus.NEW)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid order status request");
        }
    }

    public static void throwExceptionIfUserOrderIsNotOnQueue(int orderId, OrderDao orderDao) {
        Order order = orderDao.selectById(orderId).get();
        OrderStatus orderStatus = order.orderStatus();
        System.out.println(orderStatus.toString());
        if (!orderStatus.equals(OrderStatus.ONQUEUE)) {
            System.out.println("ohhh");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid order status request");
        }
    }

}
