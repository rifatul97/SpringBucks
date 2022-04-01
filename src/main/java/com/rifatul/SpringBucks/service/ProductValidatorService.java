package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.SubCategoryDao;
import com.rifatul.SpringBucks.domain.model.CartItem;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import com.rifatul.SpringBucks.exception.*;

import java.util.List;

import static com.rifatul.SpringBucks.domain.model.OrderStatus.*;

public class ProductValidatorService {

    public static void throwExceptionIfCategoryIdDoesNotExist(int categoryId, SubCategoryDao subCategoryDao) {
        if (subCategoryDao.selectById(categoryId).isEmpty()) {
            throw new CategoryDoesNotExistException(categoryId);
        }
    }

    public static void throwExceptionIfPriceIsIncorrectFormat(Double price) {
        if (price <= 0) {
            throw new IncorrectPriceFormatException("price cannot be less or equal to 0");
        }
    }

    public static void throwExceptionIfProductIdDoesNotExist(int productId, ProductDao productDao) {
        if (productDao.selectById(productId).isEmpty()) {
            throw new ProductNotExistingException(productId);
        }
    }

    public static void throwExceptionIfProductNameAlreadyExist(String name, ProductDao productDao) {
        if (!productDao.selectByName(name).isEmpty()) {
            throw new ProductNameAlreadyExistException(name);
        }
    }

    public static void throwExceptionIfProductHadBeenOrdered(int productId, OrderDao orderDao, CartDao cartDao) throws InterruptedException {
        for(OrderStatus status : List.of(NEW, ONQUEUE, FULFILLING)) {
            List<Order> orders = orderDao.selectByStatus(status);
            for (Order order : orders) {
                List<CartItem> cartItemList = cartDao.selectByOrderId(order.id());
                for (CartItem cartItem : cartItemList) {
                    if (cartItem.getProductId() == productId) {
                        throw new ProductAlreadyOnCartException(order.id(), productId);
                    }
                }
            }
        }
        Thread.sleep(2000);
    }
}