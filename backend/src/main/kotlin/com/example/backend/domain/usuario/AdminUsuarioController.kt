package com.example.backend.domain.usuario

import com.example.backend.dto.AsignarRolRequest
import com.example.backend.dto.UsuarioResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/usuarios")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
class AdminUsuarioController(private val usuarioService: UsuarioService) {

    @GetMapping
    fun getAllUsuariosWithRoles(): ResponseEntity<List<UsuarioResponse>> {
        return ResponseEntity.ok(usuarioService.findAllUsuariosWithRoles())
    }

    @PostMapping("/{userId}/roles")
    fun asignarRolAUsuario(
        @PathVariable userId: Long,
        @Valid @RequestBody request: AsignarRolRequest
    ): ResponseEntity<UsuarioResponse> {
        val usuarioActualizado = usuarioService.asignarRolAUsuario(userId, request.rolId)
        return ResponseEntity.ok(usuarioActualizado)
    }
}
