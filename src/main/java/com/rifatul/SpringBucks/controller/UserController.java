package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.domain.dto.UpdateUserRoleRequest;
import com.rifatul.SpringBucks.domain.dto.UserRegisterRequest;
import com.rifatul.SpringBucks.domain.model.User;
import com.rifatul.SpringBucks.exception.types.UserEmailAlreadyExistException;
import com.rifatul.SpringBucks.security.filters.TokenAuthenticationBuilder;
import com.rifatul.SpringBucks.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.rifatul.SpringBucks.security.filters.TokenAuthenticationBuilder.removeAuthentication;

@RestController @Slf4j
@RequestMapping(path = "/api/v1/users")
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 36000)
public class UserController {

    @Autowired private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegisterRequest newUser) {
        log.info("registering!! new user with email = {}", newUser.email());
        User user = null;
        log.info("step 2");
        try {
            log.info("step 3");
            user = userService.register(newUser);
            log.info("step 4");
            //log.info("new user name is {}", user.getFullName());
        } catch (UserEmailAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        removeAuthentication(request, response);
    }

    @GetMapping("/current")
    public ResponseEntity<String> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findUserByEmail(currentPrincipalName);
        return ResponseEntity.ok(authentication.getAuthorities().toString());
    }


    @GetMapping("/orders")
    public ResponseEntity<String> getCurrentUserOrder() {
        return ResponseEntity.ok("no orders");//orderService.getUsersOrderIdsByUserIdAndStatus(request);
    }

    @GetMapping("/hello")
    public String hello(@CurrentSecurityContext(expression="authentication?.name") String username) {
        return username;
    }

}