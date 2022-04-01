package com.rifatul.SpringBucks.domain.dto;

public final class CartDTO {

    public sealed interface Request {
        record UpdateProduct (int cartItemId, int productId, int quantity) implements Request {}
        record AddProduct (int productId, int quantity) implements Request {}
        record RemoveProduct (int cartItemId) implements Request {}
    }

//    public sealed interface Response {
//
//    }


}
