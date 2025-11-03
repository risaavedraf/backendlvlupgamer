package com.example.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    // 1. Define el Bean de PasswordEncoder para hashear contraseñas
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    // 2. Configura las reglas de seguridad HTTP
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Deshabilita CSRF para APIs REST
            .authorizeHttpRequests { auth ->
                auth
                    // Permite acceso público a registro y login
                    .requestMatchers("/api/auth/**", "/h2-console/**").permitAll() 
                    // Cualquier otra petición (ej. ver productos) requerirá autenticación
                    .anyRequest().authenticated() 
            }
            // Permite que funcione el /h2-console
            .headers { headers -> headers.frameOptions { it.sameOrigin() } } 

        return http.build()
    }
}