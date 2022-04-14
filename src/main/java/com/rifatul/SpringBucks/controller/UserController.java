package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.domain.dto.OrderDTO;
import com.rifatul.SpringBucks.domain.dto.UserDTO;
import com.rifatul.SpringBucks.domain.model.CartItem;
import com.rifatul.SpringBucks.domain.model.Order;
import com.rifatul.SpringBucks.domain.model.User;
import com.rifatul.SpringBucks.service.OrderService;
import com.rifatul.SpringBucks.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.security.filters.TokenAuthenticationBuilder.removeAuthentication;

@RestController @Slf4j
@RequestMapping(path = "/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private OrderService orderService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO.Request.Register newUser) throws ResponseStatusException{
        User user= userService.register(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        removeAuthentication(request, response);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName().equalsIgnoreCase("anonymousUser")) {
            return ResponseEntity.notFound().build();
        }
        String currentPrincipalName = authentication.getName();
        System.out.println("currentPrincipalname = " + currentPrincipalName);
        Optional<User> user = userService.findUserByEmail(currentPrincipalName);

        return ResponseEntity.ok(user.get());
    }


    @GetMapping("/orders")
    public ResponseEntity<Order> getCurrentUserOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findUserByEmail(currentPrincipalName);

        return ResponseEntity.ok(orderService.getUserOrderId(user.get().id()));
    }

    @GetMapping("/hello")
    public String hello(Principal principal ) {
        String name = principal.getName();
        return name;
    }

}