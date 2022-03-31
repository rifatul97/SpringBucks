package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.domain.dto.CartDTO;
import com.rifatul.SpringBucks.domain.dto.UserDTO;
import com.rifatul.SpringBucks.domain.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.rifatul.SpringBucks.service.UserCartItemsValidatorService.*;

public class UserCartItemsService {

    @Autowired private CartDao cartDao;
    @Autowired private OrderDao orderDao;
    @Autowired private ProductDao productDao;

    public void addProductToUserCart(CartDTO.Request.AddProduct req) {

        throwExceptionIfProductDoesNotExist(productDao, req.productId());
        throwExceptionIfOrderDoesNotExistOrInvalid(orderDao, req.orderId())
                .ifPresent(found -> checkIfOrderIsNew(found.orderStatus()));
        throwExceptionIfQuantityIsNegative(req.quantity());
        throwExceptionIfProductAlreadyOnCart(cartDao, req.orderId(), req.productId());

        cartDao.addCartItem(req.orderId(), req.productId(), req.quantity());
    }

    public void updateProductToCart(CartDTO.Request.UpdateProduct req) {
        throwExceptionIfProductDoesNotExist(productDao, req.productId());
        throwExceptionIfCartItemExist(cartDao, req.cartItemId());
        throwExceptionIfQuantityIsNegative(req.quantity());

        cartDao.updateCartItem(req.productId(), req.cartItemId(), req.quantity());
    }

    public void removeProductFromCart(CartDTO.Request.RemoveProduct request) {
        throwExceptionIfCartItemExist(cartDao, request.cartItemId());

        cartDao.deleteCartItem(request.cartItemId());
    }

    public UserDTO.Response.GetCartItems fetchUserCartItems(int orderId) {
        List<CartItem> userCartItemsInDB = cartDao.selectByOrderId(orderId);

        return new UserDTO.Response.GetCartItems(orderId, userCartItemsInDB);
    }
}
