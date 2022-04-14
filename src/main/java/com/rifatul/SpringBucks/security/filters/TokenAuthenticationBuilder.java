package com.rifatul.SpringBucks.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class TokenAuthenticationBuilder {
    private static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 minutes
    private static final String SECRET = "ThisIsASecret";
    private static final String COOKIE_BEARER = "COOKIE-BEARER";

    private TokenAuthenticationBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static void removeAuthentication(HttpServletRequest request, HttpServletResponse response) {
        boolean isSecure = false;
        String contextPath = null;
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            isSecure = request.isSecure();
            contextPath = request.getContextPath();
        }
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
        if (response != null) {
            Cookie cookie = new Cookie("COOKIE-BEARER", null);
            String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            cookie.setSecure(isSecure);
            response.addCookie(cookie);
        }

    }

    static void addAuthentication(HttpServletRequest request, HttpServletResponse res, Authentication authResult) {

        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                // .withExpiresAt(new Date(System.currentTimeMillis() + 10*1000))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Cookie cookie = new Cookie(COOKIE_BEARER, access_token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
        res.addHeader("Access-Control-Allow-Credentials", "true");
//        res.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        System.out.println("finale.." + res.getStatus());
        System.out.println("access token: " + access_token);
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_BEARER);
        String user_token = cookie != null ? cookie.getValue() : null;

        if (user_token != null) {
            System.out.println("that means there is user_token");
            //String token = user_token.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(user_token);

            String username = decodedJWT.getSubject();
            System.out.println("username tried to enter is " + username);
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority((role)));
            });
            System.out.println("his authorities are " + authorities.toString());
            return username != null ? new UsernamePasswordAuthenticationToken(username, null, authorities) : null;
        } else {
            System.out.println("NOO!! THERE IS NO user_token in header!");
        }
        return null;
    }

}