package com.rifatul.SpringBucks.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static com.rifatul.SpringBucks.security.filters.TokenAuthenticationBuilder.addAuthentication;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000", maxAge = 36000)
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    record AccountCredentials (String email, String password) {}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("attempting authentication");
            AccountCredentials creds = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
            log.info("entered: email= {} password= {}", creds.email(), creds.password());
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.email(), creds.password() //,Collections.emptyList()
            ));
        } catch (IOException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(e.getMessage());
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        addAuthentication(request, response, authResult);
        System.out.println(authResult.isAuthenticated());
        log.info(authResult.getName());
        log.info("SUCCESSFULLY AUTHENTICATED :^)");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }

}