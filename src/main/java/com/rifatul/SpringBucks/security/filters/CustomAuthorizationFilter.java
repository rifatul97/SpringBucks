package com.rifatul.SpringBucks.security.filters;

import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.rifatul.SpringBucks.security.filters.TokenAuthenticationBuilder.getAuthentication;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private static final String SECRET = "ThisIsASecret";
    private static final String COOKIE_BEARER = "COOKIE-BEARER";
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public static final List<String> NON_AUTH_END_POINTS
            = Collections.unmodifiableList(Arrays.asList("/", "/api/v1/categories", "/api/v1/products"));

    //@CrossOrigin(origins = "http://localhost:3000")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //response.addHeader("Access-Control-Allow-Credentials", "true");
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        System.out.println("request = " + request.getServletPath().toString());

        if (request.getServletPath().equals("/") || request.getServletPath().equals("/api/v1/users/login") || request.getServletPath().equals("/api/v1/users/register")){ //|| request.getServletPath().equals("/api/v1/users/register")) {
            System.out.println("filter if");
            filterChain.doFilter(request, response);
        } else {
            System.out.println("filter out");
            try {
                System.out.println("got request => " + request.getServletPath().toString());
                Authentication authentication = getAuthentication(request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                if (authentication == null) {
                    System.out.println("authentication is a NULL!!!");
                } else {
                    System.out.println("is authenticated yet? " + authentication.isAuthenticated());
                }
                filterChain.doFilter(request, response);
            }
            catch (TokenExpiredException e) {
                System.out.println("TokenExpiredException!");
                response.setStatus(400);
            }
            catch (ExpiredJwtException e) {
                System.out.println("ExpiredJwtException!");
            }
           catch (UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
                System.out.println(e.getLocalizedMessage());
            } catch(Exception e){
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                //response.setHeader("Location", "http://localhost:8080/");
                System.out.println(HttpServletResponse.SC_MOVED_PERMANENTLY);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return NON_AUTH_END_POINTS.stream().anyMatch(p -> {
            return pathMatcher.match(p, request.getServletPath())
                    && request.getMethod().equals("GET");
        });
    }


}