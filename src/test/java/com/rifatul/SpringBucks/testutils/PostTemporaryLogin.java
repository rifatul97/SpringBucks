package com.rifatul.SpringBucks.testutils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class PostTemporaryLogin {

    private static final String COOKIE_BEARER = "COOKIE-BEARER";

    public static Cookie generateTemporaryAccessCookie(TestUser testUser) {
        String access_token = generateTemporaryAccessToken(testUser);
        System.out.println("created temp access token = " + access_token);
        return new Cookie("COOKIE-BEARER", access_token);
    }

    private static String generateTemporaryAccessToken(TestUser testUser) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String token = JWT.create()
                .withSubject(testUser.email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 30000))
                .withIssuer("http://localhost:8080/api/v1/users/login")
                .withClaim("roles", List.of(testUser.getAuthority()))
                .sign(algorithm);
        return token;
    }

}
