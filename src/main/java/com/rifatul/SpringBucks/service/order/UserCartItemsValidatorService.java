package com.rifatul.SpringBucks.service.order;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.domain.model.CartItem;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import com.rifatul.SpringBucks.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.domain.model.OrderStatus.NEW;
import static com.rifatul.SpringBucks.domain.model.OrderStatus.ONQUEUE;
import static com.rifatul.SpringBucks.service.order.OrderValidatorService.throwExceptionIfUserOrderIsNotNew;
import static com.rifatul.SpringBucks.service.order.OrderValidatorService.throwExceptionIfUserOrderIsNotOnQueue;

public class UserCartItemsValidatorService {

    public static void throwExceptionIfProductDoesNotExist(final ProductDao productDao, int productId) throws ProductNotExistingException {
        if (productDao.selectById(productId).isEmpty()) {
            throw new ProductNotExistingException(productId);
        }
    }

    public static void throwExceptionIfCartItemDoesNotExist(final CartDao cartDao, int cartItemId) throws CartItemNotExistingException {
        if (cartDao.selectById(cartItemId).isEmpty()) {
            throw new CartItemNotExistingException(cartItemId);
        }
    }

    public static void checkIfOrderIsNew(OrderStatus status) throws OrderExpiredException {
        if (!status.equals(NEW)) {
            throw new OrderExpiredException(status);
        }
    }

    public static Optional<Order> throwExceptionIfOrderDoesNotExistOrInvalid(final OrderDao orderDao, long orderId) throws OrderNotExistingException {
        return Optional.ofNullable(orderDao.selectById(orderId)).orElseThrow(() -> {
            throw new OrderNotExistingException(orderId);
        });
    }

    public static void throwExceptionIfUserOrderStatusIsNotNewOrOnQueue(OrderDao orderDao, long orderId) {
        var userOrder = orderDao.selectById(orderId).get();
        if (!(userOrder.orderStatus().equals(NEW) || userOrder.orderStatus().equals(ONQUEUE))) {
            throw new OrderNotExistingException(orderId);
        }
    }

    public static void throwExceptionIfQuantityIsNegative(int quantity) throws IllegalQuantityAmountException {
        if (quantity <= 0) { throw new IllegalQuantityAmountException(); }
    }

    public static void throwExceptionIfProductAlreadyOnCart (CartDao cartDao, long orderId, int productId) {
        List<CartItem> cartItemList = cartDao.selectByOrderId(orderId);
        for (CartItem item : cartItemList) {
            System.out.println("item.productId() == productId : " + item.productId() + " vs " + productId);
            if (item.productId() == productId) {
                System.out.println("throwing the exception!!!");
                throw new ProductAlreadyOnCartException(orderId, productId);
            } else {
                System.out.println("nope not equal yet!");
            }
        }
    }

    public static void throwExceptionIfUserCartIsEmpty(int orderId, CartDao cartDao) {
        if (cartDao.selectByOrderId(orderId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot accept this request because the user cart is empty");
        }
    }

    public static void throwExceptionOnIncorrectOrderStatus(OrderDao orderDao, int orderId, OrderStatus orderStatus) {
        System.out.println("checking orderstatus of " + orderStatus.toString());
        switch (orderStatus) {
            case ONQUEUE -> {
                throwExceptionIfUserOrderIsNotNew(orderId, orderDao);
                break;
            }
            case CANCELLED -> {
                System.out.println("whoops checking this");
                throwExceptionIfUserOrderIsNotOnQueue(orderId, orderDao);
                break;
            }
        }
    }

    public static void throwExceptionProductDoesNotExistOnUserExist(int productId, int orderId) {
    }
}