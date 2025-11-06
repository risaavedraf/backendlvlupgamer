package com.example.backend.config

import com.example.backend.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    // Inyectamos nuestro filtro
    private val jwtAuthFilter: JwtAuthenticationFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    // Exponemos el AuthenticationManager que el UsuarioService necesita
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    // Rutas públicas
                    .requestMatchers(
                        "/api/auth/**",
                        "/h2-console/**"
                    ).permitAll()

                    // Rutas protegidas
                    .requestMatchers("/api/perfil/**").authenticated()
                    .requestMatchers("/api/usuarios/**").authenticated()

                    // El resto puede permanecer público según necesites
                    .anyRequest().permitAll()
            }
            // 1. Le decimos que no use Sesiones (porque usamos JWT)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .headers { headers -> headers.frameOptions { it.sameOrigin() } }
            // 2. Añadimos nuestro filtro JWT ANTES del filtro de login estándar
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}