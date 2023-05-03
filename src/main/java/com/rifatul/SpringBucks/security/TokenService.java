package com.rifatul.SpringBucks.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TokenService {

    @Autowired
    private final JwtEncoder encoder;

    public String generateToken(Authentication userAuth) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(userAuth.getName())
                .claim("role", extractRoleFromUserAuth(userAuth))
                .build();

        String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();;


//        Cookie cookie = new Cookie("token", token);
//        cookie.setPath("/");
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.addCookie(cookie);

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    private String extractRoleFromUserAuth(Authentication userAuth) {
        String role = userAuth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));;
        System.out.println("role = " + role);

        return userAuth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

}