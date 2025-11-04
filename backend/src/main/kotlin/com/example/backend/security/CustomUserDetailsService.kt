package com.example.backend.security

import com.example.backend.domain.usuario.UsuarioRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val usuarioRepository: UsuarioRepository
) : UserDetailsService {

    // Spring Security llama a esto. "username" en nuestro caso es el email.
    override fun loadUserByUsername(email: String): UserDetails {
        val usuario = usuarioRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("Usuario no encontrado con email: $email") }

        return User(
            usuario.email, // El "username" de Spring
            usuario.passwordHash, // La contraseña hasheada
            listOf() // Aquí irían los roles/autoridades si los tuviéramos
        )
    }
}