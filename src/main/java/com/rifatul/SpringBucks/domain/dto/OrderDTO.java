package com.rifatul.SpringBucks.domain.dto;

import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public final class OrderDTO {

    public sealed interface Request {
        record GetUserByStatus (int userId, OrderStatus orderStatus) implements Request {}
        record GetIdsByStatus (List<Integer> orderIds, OrderStatus status) implements Request {}
        record AddToUser (int userId, int orderId) implements Request {}
        record UpdateStatus (int orderId, OrderStatus status) implements Request {}
    }

    public sealed interface Response {
        record Get(Order userOrder) implements Response {}

    }


}
