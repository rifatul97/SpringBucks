package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.domain.dto.OrderDTO;
import com.rifatul.SpringBucks.domain.model.CartItem;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import com.rifatul.SpringBucks.service.KitchenService;
import com.rifatul.SpringBucks.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @Slf4j @RequestMapping("/api/v1/kitchen")
public class KitchenController {

    @Autowired
    OrderService orderService;
    @Autowired
    KitchenService kitchenService;

    @GetMapping("/carts")
    public ResponseEntity<List<List<CartItem>>> getAllOrdersByStatus() {
        return ResponseEntity.ok(kitchenService.getAllUserCarts());
    }
//
//    @GetMapping("/carts")
//    public ResponseEntity<List<UserCartItems>> getAllUserCarts() {
//        List<UserCartItems> allUserCarts = null;
//        try {
//            allUserCarts = kitchenService.getAllUserCarts();
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return ResponseEntity.ok(allUserCarts);
//    }

    @PostMapping("/{id}/{action}")
    public void execute(@PathVariable(name = "id") int orderId, @PathVariable(name = "orderState") String action) {
        OrderStatus status = switch (action) {
            case "start" -> OrderStatus.FULFILLING;
            case "fullfilled" -> OrderStatus.FULFILLED;
            default -> throw new IllegalStateException("Unexpected value: " + action);
        };
        kitchenService.takeOrderAction(new OrderDTO.Request.UpdateStatus(orderId, status));
    }


}