package com.rifatul.SpringBucks.controller.user;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.rifatul.SpringBucks.domain.dto.UserDTO;
import com.rifatul.SpringBucks.exception.UserNotLoggedInException;
import com.rifatul.SpringBucks.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authenticate")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> generateToken(Authentication authentication) {
        String token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/get")
    public ResponseEntity<UserDTO.Response.CurrentUser> getCurrentUserAuth(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
        if (auth == null) {
            throw new UserNotLoggedInException();
        }

        return ResponseEntity.ok(new UserDTO.Response.CurrentUser(auth.getName()));
    }


}
