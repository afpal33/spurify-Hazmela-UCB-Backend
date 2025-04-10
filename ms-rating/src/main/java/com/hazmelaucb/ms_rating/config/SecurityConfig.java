package com.hazmelaucb.ms_rating.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 🔥 Permitir TODO sin autenticación
                )
                .csrf(csrf -> csrf.disable()) // Opcional: Desactiva CSRF si es necesario
                .build();
    }
}
