package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import com.rifatul.SpringBucks.exception.*;

import java.util.Optional;

import static com.rifatul.SpringBucks.domain.model.OrderStatus.NEW;

public class UserCartItemsValidatorService {

    public static void throwExceptionIfProductDoesNotExist(final ProductDao productDao, int productId) throws ProductNotExistingException {
        if (productDao.selectById(productId).isEmpty()) {
            throw new ProductNotExistingException(productId);
        }
    }

    public static void throwExceptionIfCartItemExist(final CartDao cartDao, int cartItemId) throws CartItemNotExistingException {
        if (cartDao.selectById(cartItemId).isEmpty()) {
            throw new CartItemNotExistingException(cartItemId);
        }
    }

    public static void checkIfOrderIsNew(OrderStatus status) throws OrderExpiredException {
        if (!status.equals(NEW)) {
            throw new OrderExpiredException(status);
        }
    }

    public static Optional<Order> throwExceptionIfOrderDoesNotExistOrInvalid(final OrderDao orderDao, int orderId) throws OrderNotExistingException {
        return Optional.ofNullable(orderDao.selectById(orderId)).orElseThrow(() -> {
            throw new OrderNotExistingException(orderId);
        });
    }

    public static void throwExceptionIfQuantityIsNegative(int quantity) throws IllegalQuantityAmountException {
        if (quantity <= 0) { throw new IllegalQuantityAmountException(); }
    }

    public static void throwExceptionIfProductAlreadyOnCart (CartDao cartDao, int orderId, int productId) {
        if (orderId == productId) {
            throw new ProductAlreadyOnCartException(orderId, productId);
        }
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    public static void throwExceptionIfProductIsInUserCart(int productId) {
    ////////throw new ProductIsInUserCartException(productId);/////////////
    }//////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////


}