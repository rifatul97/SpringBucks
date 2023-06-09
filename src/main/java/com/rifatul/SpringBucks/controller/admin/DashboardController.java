package com.rifatul.SpringBucks.controller.admin;

import com.rifatul.SpringBucks.domain.dto.UserDTO;
import com.rifatul.SpringBucks.domain.model.User;
import com.rifatul.SpringBucks.service.order.UserCartItemsService;
import com.rifatul.SpringBucks.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/dashboard")
public class DashboardController {

    @Autowired private UserService userService;
    @Autowired private UserCartItemsService cartItemsService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(StreamSupport
                .stream(userService.findAllUsers().spliterator(), false)
                .collect(Collectors.toList()));
    }

    @PostMapping("/users/role/add")
    public void addUserRole(@RequestBody UserDTO.Request.AddRole request) {
        userService.addUserRole(request.userId(), request.roleId());
    }

    @PostMapping("/users/role/remove")
    public void removeUserRole(@RequestBody UserDTO.Request.RemoveRole request) {
        userService.removeUserRole(request.userId());
    }

}
