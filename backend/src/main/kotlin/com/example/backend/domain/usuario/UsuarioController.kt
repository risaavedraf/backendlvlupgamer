package com.example.backend.domain.usuario

import com.example.backend.dto.LoginRequest
import com.example.backend.dto.LoginResponse
import com.example.backend.dto.RegistroRequest
import com.example.backend.dto.UsuarioResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth") // Ruta base para autenticación
@Tag(name = "Autenticación", description = "Endpoints para registro e inicio de sesión de usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea una nueva cuenta de usuario con los datos proporcionados.")
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
    @Operation(summary = "Iniciar sesión", description = "Autentica a un usuario y devuelve un token JWT.")
    fun loginUsuario(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<LoginResponse> { // <-- ESTA ES LA LÍNEA CORRECTA
        
        // ResponseEntity.ok() devuelve un código 200
        return ResponseEntity.ok(usuarioService.login(request))
    }
}