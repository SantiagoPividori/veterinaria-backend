package com.pividori.Veterinaria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Deshabilita protección CSRF (necesario si no usás formularios)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite acceso a TODO
                )
                .formLogin(login -> login.disable()) // Deshabilita login por formulario
                .httpBasic(basic -> basic.disable()); // Deshabilita autenticación básica

        return http.build();
    }
}
