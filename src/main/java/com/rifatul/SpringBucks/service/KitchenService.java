package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.domain.dto.OrderDTO;
import com.rifatul.SpringBucks.domain.model.CartItem;
import com.rifatul.SpringBucks.domain.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.rifatul.SpringBucks.domain.model.OrderStatus.*;
import static com.rifatul.SpringBucks.service.KitchenOrderValidatorService.throwExceptionIfOrderIsNotYetOrdered;
import static com.rifatul.SpringBucks.service.KitchenOrderValidatorService.throwExceptionIfOrderNotStartedYet;
import static com.rifatul.SpringBucks.service.OrderValidatorService.throwExceptionIfUserOrderDoesNotExist;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class KitchenService {

    @Autowired private final OrderDao orderDao;
    @Autowired private final CartDao cartDao;

    public void takeOrderAction(OrderDTO.Request.UpdateStatus request) {
        throwExceptionIfUserOrderDoesNotExist(request.orderId(), orderDao);

        if (request.equals(ONQUEUE)) {
            throwExceptionIfOrderIsNotYetOrdered(request.status());
            orderDao.updateStatus(request.orderId(), FULFILLING);
        } else if (request.equals(FULFILLING)) {
            throwExceptionIfOrderNotStartedYet(request.status());
            orderDao.updateStatus(request.orderId(), FULFILLED);
        }

    }

    public List<List<CartItem>> getAllUserCarts() {
        List<Order> customerOrdersList = orderDao.selectByStatus(ONQUEUE);
        List<List<CartItem>> ordersList = Collections.emptyList();

        for (Order customerOrder : customerOrdersList) {
            List<CartItem> cartItemsByOrderId = cartDao.selectByOrderId(customerOrder.id());
            ordersList.add(cartItemsByOrderId);
        }

        return ordersList;
    }

}