package com.example.backend.domain.usuario

import com.example.backend.dto.UpdateProfileRequest
import com.example.backend.dto.UsuarioResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ProfileService(
    private val usuarioRepository: UsuarioRepository
) {
    fun getProfile(email: String): UsuarioResponse {
        val usuario = usuarioRepository.findByEmail(email)
            .orElseThrow { EntityNotFoundException("Usuario no encontrado") }

        // Asume que la función "toResponse()" está en "UsuarioService.kt"
        // y se importa automáticamente por estar en el mismo paquete.
        return usuario.toResponse()
    }

    fun updateProfile(email: String, request: UpdateProfileRequest): UsuarioResponse {
        val usuario = usuarioRepository.findByEmail(email)
            .orElseThrow { EntityNotFoundException("Usuario no encontrado") }

        // Actualizamos los campos
        usuario.name = request.name
        usuario.lastName = request.lastName
        usuario.birthDate = LocalDate.parse(request.birthDate)

        // Guardamos los cambios
        val usuarioGuardado = usuarioRepository.save(usuario)
        return usuarioGuardado.toResponse()
    }
}