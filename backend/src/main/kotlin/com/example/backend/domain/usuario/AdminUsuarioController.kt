package com.example.backend.domain.usuario

import com.example.backend.dto.AsignarRolRequest
import com.example.backend.dto.PageResponse
import com.example.backend.dto.UsuarioResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/usuarios")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
@Tag(name = "Administración de Usuarios", description = "Endpoints para gestión de usuarios por administradores")
class AdminUsuarioController(private val usuarioService: UsuarioService) {

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene una lista paginada de usuarios con sus roles.")
    @Parameters(
        Parameter(name = "page", description = "Número de página (0..N)", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "0")),
        Parameter(name = "size", description = "Tamaño de página", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "10")),
        Parameter(name = "sort", description = "Criterio de ordenamiento (ej. username,asc)", `in` = ParameterIn.QUERY, schema = Schema(type = "string", defaultValue = "username,asc"))
    )
    fun getAllUsuariosWithRoles(
        @RequestParam(required = false) query: String?,
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = ["username"]) pageable: Pageable
    ): ResponseEntity<PageResponse<UsuarioResponse>> {
        return ResponseEntity.ok(usuarioService.findAllUsuariosWithRoles(pageable, query))
    }

    @PostMapping("/{userId}/roles")
    @Operation(summary = "Asignar rol a usuario", description = "Asigna un rol específico a un usuario existente.")
    fun asignarRolAUsuario(
        @PathVariable userId: Long,
        @Valid @RequestBody request: AsignarRolRequest
    ): ResponseEntity<UsuarioResponse> {
        val usuarioActualizado = usuarioService.asignarRolAUsuario(userId, request.rolId)
        return ResponseEntity.ok(usuarioActualizado)
    }

    @DeleteMapping("/{userId}/roles/{rolId}")
    @Operation(summary = "Remover rol de usuario", description = "Elimina un rol específico asignado a un usuario.")
    fun removerRolDeUsuario(
        @Parameter(description = "ID del usuario", required = true) @PathVariable userId: Long,
        @Parameter(description = "ID del rol a remover", required = true) @PathVariable rolId: Long
    ): ResponseEntity<UsuarioResponse> {
        val usuarioActualizado = usuarioService.removerRolDeUsuario(userId, rolId)
        return ResponseEntity.ok(usuarioActualizado)
    }
}
