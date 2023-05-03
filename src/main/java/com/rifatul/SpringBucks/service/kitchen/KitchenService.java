package com.rifatul.SpringBucks.service.kitchen;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.rifatul.SpringBucks.domain.model.OrderStatus.*;
import static com.rifatul.SpringBucks.service.kitchen.KitchenOrderValidatorService.*;
import static com.rifatul.SpringBucks.service.order.OrderValidatorService.throwExceptionIfUserOrderDoesNotExist;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class KitchenService {

    @Autowired private final OrderDao orderDao;
    @Autowired private final CartDao cartDao;

    public void takeOrderAction(int orderId, OrderStatus requestedStatusChange) {
        System.out.println("id = " + orderId + " request = " + requestedStatusChange);
        throwExceptionIfUserOrderDoesNotExist(orderId, orderDao);

        if (requestedStatusChange.equals(FULFILLING)) {
            throwExceptionIfOrderIsNotYetOrdered(orderId, orderDao);
            orderDao.updateStatus(orderId, FULFILLING);
        } else if (requestedStatusChange.equals(COMPLETED)) {
            throwExceptionIfOrderNotStartToProcessYet(orderId, orderDao);
            orderDao.updateStatus(orderId, COMPLETED);
        }
    }

    public List<Order> getCustomerOrderList() {
        List<Order> customerOrdersList = new ArrayList<>();
        customerOrdersList.addAll(orderDao.selectByStatus(ONQUEUE));
        customerOrdersList.addAll(orderDao.selectByStatus(FULFILLING));
        return customerOrdersList;
    }
//
//    public List<List<CartItem>> getAllUserCarts() {
//        log.info("customerOrdersList : {}", customerOrdersList.toString());
//        List<List<CartItem>> ordersList = new ArrayList<>();
//
//        for (Order customerOrder : customerOrdersList) {
//            List<CartItem> cartItemsByOrderId = cartDao.selectByOrderId(customerOrder.id());
//            ordersList.add(cartItemsByOrderId);
//        }
//
//        return ordersList;
//    }

}