package com.rifatul.SpringBucks.domain.dto;

import com.rifatul.SpringBucks.domain.model.CartItem;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.Product;

import java.util.List;

public final class CartDTO {

    public sealed interface Request {
        record UpdateProduct (int cartItemId, int productId, int quantity) implements Request {}
        record AddProduct (int productId, int quantity) implements Request {}
        record RemoveProduct (int cartItemId) implements Request {}
    }

    public sealed interface Response {
        record ItemById (int cartItemId, Product product, int quantity) implements Response {}
        record Items (Order order, List<ItemById> items) implements Response {}
        record GetAllOrders (List<Response.Items> customerOrders) implements Response {}
    }


}
