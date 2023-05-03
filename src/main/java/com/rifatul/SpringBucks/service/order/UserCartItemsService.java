package com.rifatul.SpringBucks.service.order;

import com.rifatul.SpringBucks.dao.OrderDao;
import com.rifatul.SpringBucks.dao.ProductDao;
import com.rifatul.SpringBucks.dao.CartDao;
import com.rifatul.SpringBucks.domain.dto.CartDTO;
import com.rifatul.SpringBucks.domain.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.service.order.UserCartItemsValidatorService.*;

@Service
public class UserCartItemsService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    public void addProductToUserCart(long orderId, int productId, int quantity) {
        throwExceptionIfProductDoesNotExist(productDao, productId);
        throwExceptionIfOrderDoesNotExistOrInvalid(orderDao, orderId)
                .ifPresent(found -> checkIfOrderIsNew(found.orderStatus()));
        throwExceptionIfUserOrderStatusIsNotNewOrOnQueue(orderDao, orderId);
        throwExceptionIfQuantityIsNegative(quantity);
        throwExceptionIfProductAlreadyOnCart(cartDao, orderId, productId);

        cartDao.addCartItem(orderId, productId, quantity);
    }

    public void updateProductToCart(long orderId, int productId, int quantity) {
        throwExceptionIfProductDoesNotExist(productDao, productId);
        throwExceptionIfQuantityIsNegative(quantity);
        throwExceptionIfUserOrderStatusIsNotNewOrOnQueue(orderDao, orderId);
        int cartItemId = getProductFromUserCart(orderId, productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "user did not put this product on the cart yet"));

        cartDao.updateCartItem(cartItemId, productId, quantity);
    }


    private Optional<Integer> getProductFromUserCart(long orderId, int productId) {
        for (var cartItem : fetchUserCartItems(orderId)) {
            if (cartItem.product().getId() == productId) {
                return Optional.of(cartItem.cartItemId());
            }
        }
        return Optional.empty();
    }


    public void removeProductFromCart(int cartItemId) {
        throwExceptionIfCartItemDoesNotExist(cartDao, cartItemId);

        cartDao.deleteCartItem(cartItemId);
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
