package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.domain.dto.CartDTO;
import com.rifatul.SpringBucks.domain.model.CartItem;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.User;
import com.rifatul.SpringBucks.service.OrderService;
import com.rifatul.SpringBucks.service.UserCartItemsService;
import com.rifatul.SpringBucks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/users/cart")
public class UserCartController {

    @Autowired private UserCartItemsService cartItemsService;
    @Autowired private UserService userService;
    @Autowired private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<CartItem>> getUserCartItems () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findUserByEmail(currentPrincipalName);
        Order order = orderService.getUserOrderId(user.get().id());


        return ResponseEntity.ok(cartItemsService.fetchUserCartItems(order.id()));
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestBody CartDTO.Request.AddProduct request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findUserByEmail(currentPrincipalName);
        Order order = orderService.getUserOrderId(user.get().id());


        cartItemsService.addProductToUserCart( order.id(), request.productId(), request.quantity());
    }

    @PostMapping("/update")
    public void updateProductToCart(@RequestBody CartDTO.Request.UpdateProduct request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findUserByEmail(currentPrincipalName);
        Order order = orderService.getUserOrderId(user.get().id());

        cartItemsService.updateProductToCart(order.id(), request.cartItemId(), request.productId(), request.quantity());
    }

    @PostMapping("/remove")
    public void deleteProductToCart(@RequestBody CartDTO.Request.RemoveProduct request) {
        cartItemsService.removeProductFromCart(request);
    }


}
