package com.example.backend.config

import com.example.backend.domain.rol.Rol
import com.example.backend.domain.rol.RolRepository
import com.example.backend.domain.usuario.Usuario
import com.example.backend.domain.usuario.UsuarioRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

@Configuration
@Profile("dev")
class DataInitializer(
    private val rolRepository: RolRepository,
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Bean
    fun initDatabase(): CommandLineRunner {
        return CommandLineRunner {
            // Crear roles si no existen
            val userRole = rolRepository.findByNombre("ROLE_USER").orElseGet {
                rolRepository.save(Rol(nombre = "ROLE_USER"))
            }
            val adminRole = rolRepository.findByNombre("ROLE_ADMIN").orElseGet {
                rolRepository.save(Rol(nombre = "ROLE_ADMIN"))
            }

            // Crear usuario administrador si no existe
            if (!usuarioRepository.existsByEmail("admin@example.com")) {
                val adminUser = Usuario(
                    username = "admin",
                    email = "admin@example.com",
                    passwordHash = passwordEncoder.encode("admin123"),
                    name = "Admin",
                    lastName = "User",
                    roles = mutableSetOf(userRole, adminRole)
                )
                usuarioRepository.save(adminUser)
            }

            // Crear usuario de prueba si no existe
            if (!usuarioRepository.existsByEmail("optimizado@example.com")) {
                val testUser = Usuario(
                    username = "testoptimizado",
                    email = "optimizado@example.com",
                    passwordHash = passwordEncoder.encode("password123"),
                    name = "Test",
                    lastName = "Optimizado",
                    birthDate = LocalDate.parse("1999-12-31"),
                    roles = mutableSetOf(userRole) // Asignar solo rol de usuario
                )
                usuarioRepository.save(testUser)
            }
        }
    }
}