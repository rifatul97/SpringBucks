package com.rifatul.SpringBucks.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static com.rifatul.SpringBucks.security.filters.TokenAuthenticationBuilder.addAuthentication;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            System.out.println("attempting NOW ---");
            AccountCredentials creds = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
            log.info("{}", creds.getEmail());
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword() //,Collections.emptyList()
            ));
        } catch (IOException e) {
            response.setStatus(333);
            response.setContentType(e.getMessage());
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        addAuthentication(request, response, authResult);
//        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//        response.addHeader("Access-Control-Allow-Credentials", "true");

//        response.addHeader("Location", "http://localhost:3000/");
        System.out.println("SUCCESSFULLY AUTHENTICATED :^)");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }

}