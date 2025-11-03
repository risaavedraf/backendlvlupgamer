package com.example.backend.domain.usuario

import com.example.backend.dto.LoginRequest
import com.example.backend.dto.RegistroRequest
import com.example.backend.dto.UsuarioResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth") // Ruta base para autenticación
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    @PostMapping("/register")
    fun registrarUsuario(
        // @Valid activa las reglas de validación (ej. @NotBlank) del DTO
        @Valid @RequestBody request: RegistroRequest
    ): ResponseEntity<UsuarioResponse> {
        
        //ResponseEntity.status(HttpStatus.CREATED) devuelve un código 201
        return ResponseEntity
            .status(HttpStatus.CREATED) 
            .body(usuarioService.registrar(request))
    }

    @PostMapping("/login")
    fun loginUsuario(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<UsuarioResponse> {
        
        // ResponseEntity.ok() devuelve un código 200
        return ResponseEntity.ok(usuarioService.login(request))
    }
}