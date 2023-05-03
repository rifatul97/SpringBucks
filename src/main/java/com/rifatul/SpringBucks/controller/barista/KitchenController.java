package com.rifatul.SpringBucks.controller.barista;

import com.rifatul.SpringBucks.domain.dto.CartDTO;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import com.rifatul.SpringBucks.service.kitchen.KitchenService;
import com.rifatul.SpringBucks.service.order.OrderService;
import com.rifatul.SpringBucks.service.order.UserCartItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/v1/kitchen/orders")
public class KitchenController {

    @Autowired
    OrderService orderService;
    @Autowired
    KitchenService kitchenService;
    @Autowired
    UserCartItemsService cartItemsService;

    @PreAuthorize("hasAnyRole('BARISTA')")
    @GetMapping
    public ResponseEntity<CartDTO.Response.GetAllOrders> getAllOrdersByStatus() {
        return ResponseEntity.ok(new CartDTO.Response.GetAllOrders(
                kitchenService.getCustomerOrderList().
                        stream()
                        .map(order -> new CartDTO.Response.Items(order, cartItemsService.fetchUserCartItems(order.id())))
                        .collect(Collectors.toList())));
    }

    record KitchenAction(int orderId, String action) {}

    @PreAuthorize("hasAnyRole('BARISTA')")
    @PostMapping
    public ResponseEntity<?> takeAction(@RequestBody KitchenAction kitchenAction) {
        OrderStatus orderStatus = kitchenAction.action.equals("start") ? OrderStatus.FULFILLING : OrderStatus.COMPLETED;
        System.out.println("requested action = " + orderStatus.toString());
        kitchenService.takeOrderAction(kitchenAction.orderId(), orderStatus);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('BARISTA')")
    @PostMapping("/{id}/start")
    public ResponseEntity startOrderProcess(@PathVariable int id) {
        System.out.println("id was a " + id);
        kitchenService.takeOrderAction(id, OrderStatus.FULFILLING);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('BARISTA')")
    @PostMapping("/{id}/complete")
    public ResponseEntity markOrderCompleted(@PathVariable int id) {
        System.out.println("id was a " + id);
        kitchenService.takeOrderAction(id, OrderStatus.COMPLETED);
        return ResponseEntity.ok().build();
    }


}