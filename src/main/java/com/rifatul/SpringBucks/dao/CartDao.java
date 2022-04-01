package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartDao {
    void addCartItem(long orderId, int productId, int quantity);
    void updateCartItem (int cartItemId, int productId, int quantity);
    void deleteCartItem (int cartItemId);
    Optional<CartItem> selectById(int cartId);
    List<CartItem> selectByOrderId(long orderId);
}