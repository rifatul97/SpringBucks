package com.rifatul.SpringBucks.controller.user;

import com.rifatul.SpringBucks.domain.dto.CartDTO;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.service.order.OrderService;
import com.rifatul.SpringBucks.service.order.UserCartItemsService;
import com.rifatul.SpringBucks.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/users/cart")
public class UserCartController {

    @Autowired private UserCartItemsService cartItemsService;
    @Autowired private UserService userService;
    @Autowired private OrderService orderService;


    @GetMapping
    public ResponseEntity<CartDTO.Response.Items> getUserCartItems (Authentication auth) {
        int userId = userService.findUserByEmail(auth.getName()).get().id();

        Order userOrder = orderService.getUserOrderId(userId);

        return ResponseEntity.ok(new CartDTO.Response.Items(userOrder, cartItemsService.fetchUserCartItems(userOrder.id())));
    }

    @PostMapping("/add")
    public void addProductToCart(Authentication auth, @RequestBody CartDTO.Request.AddProduct request) {
        int userId = userService.findUserByEmail(auth.getName()).get().id();

        Order order = orderService.getUserOrderId(userId);

        cartItemsService.addProductToUserCart(order.id(), request.productId(), request.quantity());
    }

    @PostMapping("/update")
    public void updateProductToCart(Authentication auth, @RequestBody CartDTO.Request.UpdateProduct request) {
        int userId = userService.findUserByEmail(auth.getName()).get().id();

        Order order = orderService.getUserOrderId(userId);

        cartItemsService.updateProductToCart(order.id(), request.productId(), request.quantity());
    }

    @PostMapping("/remove")
    public void deleteProductToCart(@RequestBody CartDTO.Request.RemoveProduct request) {
        cartItemsService.removeProductFromCart(request.cartItemId());
    }


}
