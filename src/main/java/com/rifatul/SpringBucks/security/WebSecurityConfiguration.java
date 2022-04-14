package com.rifatul.SpringBucks.security;


import com.rifatul.SpringBucks.security.filters.CustomAuthenticationFilter;
import com.rifatul.SpringBucks.security.filters.CustomAuthorizationFilter;
import com.rifatul.SpringBucks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(final WebSecurity web) {
//        web.ignoring().requestMatchers(PUBLIC_URLS);
//    }

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(Arrays.asList("authorization", "withCredentials","content-type", "x-auth-token","Access-Control-Allow-Credentials","access-control-allow-origin","Access-Control-Allow-headers"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token","set-cookie"));
//        configuration.setMaxAge(Duration.ofSeconds(5000));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter("/api/v1/users/login", authenticationManager());

        //customAuthenticationFilter.setFilterProcessesUrl("/api/user/login");

        http.cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/v1/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.POST, "/").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/v1/users/current").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/v1/users/hello").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/logout").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/orders").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/users/carts/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users/carts").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/categories/search").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/products/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/dashboard/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/kitchen/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/kitchen/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/orders/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/orders/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().csrf().disable().formLogin().loginPage("/login").permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    AccessDeniedHandler defaultAccessDeniedHandler = new AccessDeniedHandlerImpl();
                    defaultAccessDeniedHandler.handle(request, response, accessDeniedException); // handle the custom acessDenied class here
                })
                .and()
                .addFilterAfter(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                ;
    }

    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/"), new AntPathRequestMatcher("/public/**")
    );

    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);


    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        //successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

}

