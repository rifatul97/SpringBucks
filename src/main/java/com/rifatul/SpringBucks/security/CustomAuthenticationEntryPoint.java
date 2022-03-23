package com.rifatul.SpringBucks.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.json.Json;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println(request.getRequestURL());
        System.out.println("CustomAuthenticationEntryPoint!?");

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(String.valueOf(Json.createObjectBuilder() //my util class for creating json strings
                .add("timestamp", "today")
                .add("status", 403)
                .add("message", "Access denied")
                .build()));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AccessDeniedException accessDeniedException) throws IOException {
        System.out.println(String.format("AccessDenied error: {}", accessDeniedException.getMessage()));
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.getWriter().write("very bad request!");
    }

}