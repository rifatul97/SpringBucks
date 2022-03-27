package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.domain.dto.UpdateUserRoleRequest;
import com.rifatul.SpringBucks.domain.model.User;
import com.rifatul.SpringBucks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/dashboard")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DashboardController {

    @Autowired private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(StreamSupport
                .stream(userService.findAllUsers().spliterator(), false)
                .collect(Collectors.toList()));
    }

}
