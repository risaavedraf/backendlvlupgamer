package com.example.backend.domain.usuario

import com.example.backend.dto.LoginRequest
import com.example.backend.dto.RegistroRequest
import com.example.backend.dto.UsuarioResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder // Inyectamos el encoder
) {

    fun registrar(req: RegistroRequest): UsuarioResponse {
        // 1. Validar si el usuario o email ya existen
        if (usuarioRepository.existsByUsername(req.username)) {
            throw IllegalArgumentException("El nombre de usuario '${req.username}' ya está en uso.")
        }
        if (usuarioRepository.existsByEmail(req.email)) {
            throw IllegalArgumentException("El email '${req.email}' ya está registrado.")
        }

        // 2. Crear el nuevo usuario
        val usuario = Usuario(
            username = req.username,
            email = req.email,
            // 3. Hashear la contraseña ANTES de guardarla
            passwordHash = passwordEncoder.encode(req.password),
            roles = "USER"
        )

        // 4. Guardar en la base de datos
        val usuarioGuardado = usuarioRepository.save(usuario)

        // 5. Devolver el DTO de respuesta (sin la contraseña)
        return usuarioGuardado.toResponse()
    }

    fun login(req: LoginRequest): UsuarioResponse {
        // 1. Encontrar al usuario por email
        val usuario = usuarioRepository.findByEmail(req.email)
            .orElseThrow { IllegalArgumentException("Email o contraseña incorrectos.") }

        // 2. Verificar la contraseña
        // Compara la contraseña de la petición (raw) con la hasheada (BD)
        if (!passwordEncoder.matches(req.password, usuario.passwordHash)) {
            throw IllegalArgumentException("Email o contraseña incorrectos.")
        }

        // 3. Login exitoso, devolver datos del usuario
        return usuario.toResponse()
    }
}

// Función de extensión simple para convertir Entidad -> DTO
fun Usuario.toResponse(): UsuarioResponse {
    return UsuarioResponse(
        id = this.id!!,
        username = this.username,
        email = this.email,
        roles = this.roles
    )
}