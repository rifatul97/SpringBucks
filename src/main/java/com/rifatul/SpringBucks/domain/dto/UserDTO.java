package com.rifatul.SpringBucks.domain.dto;

import com.rifatul.SpringBucks.domain.model.CartItem;
import com.rifatul.SpringBucks.domain.model.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

public final class UserDTO {

    public sealed interface Request {
        record Register (String firstname, String lastname, String email, String password) implements Request {}
        record LoginInfo (String email, String password) implements Request {}
        record UpdateRole (int userId, int roleId) implements Request {}
    }
    public sealed interface Response {
         record GetOrderId (int orderId, Timestamp dateCreated, Timestamp lastUpdated, OrderStatus orderStatus) implements Response {}
         record GetCartItems (int orderId, List<CartItem> orderItemList) implements Response {}
//        record Private(String firstname, String lastname, String email, String password) implements Response {}
//        record Public (String email, String password) implements Response {}
    }

}
