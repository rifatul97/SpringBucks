package com.rifatul.SpringBucks.security;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    JwtCustomAuthoritiesConverter jwtCustomAuthoritiesConverter;

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtCustomAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/v1/categories/**").permitAll();
            auth.requestMatchers("/api/v1/products/**").permitAll();
            auth.requestMatchers("/api/v1/users/cart/**").hasRole("CUSTOMER");
            auth.requestMatchers( "/api/v1/authenticate/**").permitAll();
            auth.requestMatchers("/api/v1/user/register").permitAll();
            auth.anyRequest().authenticated();
        });

        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.oauth2ResourceServer((oauth2) ->
                        oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));


//        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        http.exceptionHandling((ex) -> {
            ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
            ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
        });

        return http.build();
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityFilterChain tokenSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors();

        http.securityMatcher(new AntPathRequestMatcher("/api/v1/authenticate"))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.exceptionHandling(ex -> {
            ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
            ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
        });

        http.httpBasic();

        return http.build();
    }

}


