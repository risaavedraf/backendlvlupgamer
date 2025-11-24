package com.example.backend.domain.usuario

import com.example.backend.dto.FullProfileResponse // <-- Importar
import com.example.backend.dto.UpdateProfileRequest
import com.example.backend.dto.UsuarioResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/perfil")
@Tag(name = "Perfil de Usuario", description = "Endpoints para gestión del perfil del usuario autenticado")
class ProfileController(
    private val profileService: ProfileService
) {

    // GET /api/perfil/me
    @GetMapping("/me")
    @Operation(summary = "Obtener mi perfil", description = "Obtiene la información del perfil del usuario autenticado.")
    fun getMyProfile(principal: Principal): ResponseEntity<FullProfileResponse> { // <-- CAMBIO AQUÍ
        val usuario = profileService.getProfile(principal.name)
        return ResponseEntity.ok(usuario)
    }

    // PUT /api/perfil/me
    @PutMapping("/me")
    @Operation(summary = "Actualizar mi perfil", description = "Actualiza la información del perfil del usuario autenticado.")
    fun updateMyProfile(
        principal: Principal,
        @Valid @RequestBody request: UpdateProfileRequest
    ): ResponseEntity<UsuarioResponse> {
        val usuarioActualizado = profileService.updateProfile(principal.name, request)
        return ResponseEntity.ok(usuarioActualizado)
    }
}