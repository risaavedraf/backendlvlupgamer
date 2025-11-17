package com.example.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        
        // Permitir peticiones desde el frontend
        config.allowCredentials = true
        config.addAllowedOriginPattern("http://localhost:*") // Permite cualquier puerto en localhost
        config.addAllowedOriginPattern("http://127.0.0.1:*") // También desde 127.0.0.1
        config.addAllowedOriginPattern("https://localhost:*") // HTTPS localhost
        
        // Permitir todos los headers
        config.addAllowedHeader("*")
        
        // Permitir todos los métodos HTTP (incluyendo OPTIONS)
        config.addAllowedMethod("GET")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("DELETE")
        config.addAllowedMethod("OPTIONS")
        config.addAllowedMethod("PATCH")
        
        // Exponer headers de autenticación
        config.addExposedHeader("Authorization")
        config.addExposedHeader("Content-Type")
        config.addExposedHeader("Access-Control-Allow-Origin")
        config.addExposedHeader("Access-Control-Allow-Credentials")
        
        // Tiempo de caché para preflight
        config.maxAge = 3600L
        
        // Configurar para todas las rutas
        source.registerCorsConfiguration("/**", config)
        
        return CorsFilter(source)
    }
}
