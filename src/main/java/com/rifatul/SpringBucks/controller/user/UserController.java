package com.rifatul.SpringBucks.controller.user;

import com.rifatul.SpringBucks.domain.dto.UserDTO;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.OrderStatus;
import com.rifatul.SpringBucks.domain.model.User;
import com.rifatul.SpringBucks.service.order.OrderService;
import com.rifatul.SpringBucks.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static com.rifatul.SpringBucks.domain.model.OrderStatus.CANCELLED;

@RestController @Slf4j
@RequestMapping(path = "/api/v1/user")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private OrderService orderService;

    @Autowired
    private HttpServletResponse response;
    @Autowired
    HttpServletRequest request;

    @GetMapping
    public ResponseEntity<UserDTO.Response.CurrentUser> getCurrentUser(Authentication auth) {
        User user = userService.findUserByEmail(auth.getName()).get();

        return ResponseEntity.ok(new UserDTO.Response.CurrentUser(user.lastname()));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO.Request.Register newUser) throws ResponseStatusException {
        User user= userService.register(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/orders")
    public ResponseEntity<Order> getCurrentUserOrder(Authentication auth) {
        String currentPrincipalName = auth.getName();

        int currentLoggedInUserId = userService.findUserByEmail(auth.getName()).get().id();

        return ResponseEntity.ok(orderService.getUserOrderId(currentLoggedInUserId));
    }

    @PostMapping("/order")
    public ResponseEntity<?> processUserOrder(Authentication auth, @RequestParam(value = "action") String action) {
        if (!(action.equals("place") || action.equals("cancel"))) {
            return ResponseEntity.badRequest().build();
        }
        int currentLoggedInUserId = userService.findUserByEmail(auth.getName()).get().id();

        Order userOrder = orderService.getUserOrderId(currentLoggedInUserId);

        OrderStatus requestedOrder = action.equals("place") ? OrderStatus.ONQUEUE : CANCELLED;

        orderService.updateUserOrderStatus((int) userOrder.id(), requestedOrder);
        System.out.println("okay");
        return ResponseEntity.ok().build();
    }

}