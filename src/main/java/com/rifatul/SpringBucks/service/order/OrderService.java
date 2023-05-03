package com.rifatul.SpringBucks.service.order;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.rifatul.SpringBucks.domain.model.OrderStatus.CANCELLED;
import static com.rifatul.SpringBucks.service.order.OrderValidatorService.*;
import static com.rifatul.SpringBucks.service.order.UserCartItemsValidatorService.throwExceptionIfUserCartIsEmpty;
import static com.rifatul.SpringBucks.service.order.UserCartItemsValidatorService.throwExceptionOnIncorrectOrderStatus;
import static com.rifatul.SpringBucks.service.user.UserValidatorService.throwExceptionIfUserIdDoesNotExist;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    @Autowired private final OrderDao orderDao;
    @Autowired private final UserDao userDao;
    @Autowired private final CartDao cartDao;

    public Order getUserOrderId(int userId) {
        throwExceptionIfUserIdDoesNotExist(userId, userDao);

        Order order = orderDao.selectByUserId(userId)
                        .orElseGet(() -> createNewUserOrderId(userId));
        System.out.println(order.orderStatus());
        if (order.orderStatus().equals(OrderStatus.COMPLETED)) {
            long diff = order.lastUpdated().getTime();

//                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            System.out.println(System.currentTimeMillis() - seconds + " seconds ago order completed");
            if (System.currentTimeMillis() - seconds > 20) {
                orderDao.archiveUserCompletedOrder(userId, (int) order.id());
                orderDao.deleteUserOrderLink(userId);
                return createNewUserOrderId(userId);
            }
        }
        else if (order.orderStatus().equals(CANCELLED)) {
            orderDao.deleteUserOrderLink(userId);
            return createNewUserOrderId(userId);
        }
        System.out.println("hhi");
        return order;
    }

    public void updateUserOrderStatus(int orderId, OrderStatus orderStatus) {
        throwExceptionIfUserOrderDoesNotExist(orderId, orderDao);
        throwExceptionOnIncorrectOrderStatus(orderDao, orderId, orderStatus);
        throwExceptionIfUserCartIsEmpty(orderId, cartDao);

        orderDao.updateStatus(orderId, orderStatus);
    }


    private Order createNewUserOrderId(int userId) {
        log.info("creating new user order Id!");
        long newOrderId = orderDao.createNewId();
        orderDao.mapIdToUser(userId, newOrderId);
        return orderDao.selectById(newOrderId).get();
    }

}