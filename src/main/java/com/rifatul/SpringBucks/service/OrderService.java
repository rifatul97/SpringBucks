package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.domain.dto.OrderDTO;
import com.rifatul.SpringBucks.domain.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.rifatul.SpringBucks.service.OrderValidatorService.throwExceptionIfUserOrderDoesNotExist;
import static com.rifatul.SpringBucks.service.OrderValidatorService.throwExceptionIfUserOrderIsNotNew;
import static com.rifatul.SpringBucks.service.UserValidatorService.throwExceptionIfUserIdDoesNotExist;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    @Autowired private final OrderDao orderDao;
    @Autowired private final UserDao userDao;

    public Order getUserOrderId(int userId) {
        throwExceptionIfUserIdDoesNotExist(userId, userDao);

        return orderDao.selectByUserId(userId)
                .orElseGet(() -> createNewUserOrderId(userId));
    }

    public void updateOrderLastUpdated(OrderDTO.Request.UpdateStatus request) {
        throwExceptionIfUserOrderDoesNotExist(request.orderId(), orderDao);
        throwExceptionIfUserOrderIsNotNew(request.orderId(), orderDao);

        orderDao.updateStatus(request.orderId(), request.status());
    }


    private Order createNewUserOrderId(int userId) {
        long newOrderId = orderDao.createNewId();
        orderDao.mapIdToUser(userId, newOrderId);
        return orderDao.selectById(newOrderId).get();
    }

}