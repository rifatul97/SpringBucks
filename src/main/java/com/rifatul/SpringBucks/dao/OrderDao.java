package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Long createNewId();
    Optional<Order> selectById(long id);
    List<Order> selectByStatus(OrderStatus status);
    Optional<Order> selectByUserId(int userId);
    void mapIdToUser(int userId, long orderId);
    void updateStatus(int orderId, OrderStatus orderStatus);
    void deleteUserOrderLink(int userId);
    void archiveUserCompletedOrder(int userId, int orderId);
}