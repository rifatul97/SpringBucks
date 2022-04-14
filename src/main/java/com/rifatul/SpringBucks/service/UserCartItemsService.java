package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.domain.dto.CartDTO;
import com.rifatul.SpringBucks.domain.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.rifatul.SpringBucks.service.UserCartItemsValidatorService.*;

@Service
public class UserCartItemsService {

    @Autowired private CartDao cartDao;
    @Autowired private OrderDao orderDao;
    @Autowired private ProductDao productDao;

    public void addProductToUserCart(long orderId, int productId, int quantity) {
        throwExceptionIfProductDoesNotExist(productDao, productId);
        throwExceptionIfOrderDoesNotExistOrInvalid(orderDao, orderId)
                .ifPresent(found -> checkIfOrderIsNew(found.orderStatus()));
        throwExceptionIfQuantityIsNegative(quantity);
        throwExceptionIfProductAlreadyOnCart(cartDao, orderId, productId);

        cartDao.addCartItem(orderId, productId, quantity);
    }

    public void updateProductToCart(long orderId, int cartItemId, int productId, int quantity) {
        throwExceptionIfProductDoesNotExist(productDao, productId);
        throwExceptionIfCartItemDoesNotExist(cartDao, cartItemId);
        throwExceptionIfQuantityIsNegative(quantity);
        throwExceptionIfProductAlreadyOnCart(cartDao, orderId, productId);

        cartDao.updateCartItem(cartItemId, productId, quantity);
    }

    public void removeProductFromCart(CartDTO.Request.RemoveProduct request) {
        throwExceptionIfCartItemDoesNotExist(cartDao, request.cartItemId());

        cartDao.deleteCartItem(request.cartItemId());
    }

    public List<CartDTO.Response.ItemById> fetchUserCartItems(long orderId) {
        List<CartItem> userCartItems = cartDao.selectByOrderId(orderId);
        List<CartDTO.Response.ItemById> item = new ArrayList<>();
        for (CartItem cartItem : userCartItems) {
            item.add(new CartDTO.Response.ItemById(cartItem.id(), productDao.selectById(cartItem.productId()).get(), cartItem.quantity()));
        }

        return item;
    }
    /**
     record ItemById (int cartItemId, String productName, int quantity) implements Response {}
     record Items (Order order, List<ItemById> items) implements Response {} <------
     */
}
