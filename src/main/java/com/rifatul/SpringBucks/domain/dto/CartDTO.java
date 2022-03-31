package com.rifatul.SpringBucks.domain.dto;

import com.rifatul.SpringBucks.domain.model.OrderStatus;

import java.sql.Timestamp;

public final class CartDTO {

    public sealed interface Request {
        record UpdateProduct (int cartItemId, int productId, int quantity) implements Request {}
        record AddProduct (int orderId, int productId, int quantity) implements Request {}
        record RemoveProduct (int cartItemId, int productId) implements Request {}
    }

//    public sealed interface Response {
//
//    }


}
