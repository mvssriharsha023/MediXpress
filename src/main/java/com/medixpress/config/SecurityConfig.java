package com.medixpress.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/register",
                                "/user/login",
                                "/api/pharmacies",
                                "/api/pharmacies/*",
                                "/api/medicines",
                                "/api/medicines/*",
                                "/api/medicines/reduce/**",
                                "/api/cart/*",
                                "/api/cart/clear/*",
                                "/api/cart/remove/*",
                                "api/cart/update/*",
                                "/api/cart/user/*",
                                "/api/orders/place/*",
                                "/api/orders/user/*",
                                "/api/orders/*"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
