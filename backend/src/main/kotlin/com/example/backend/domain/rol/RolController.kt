package com.example.backend.domain.rol

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Endpoints para gestión de roles (Admin)")
class RolController(private val rolService: RolService) {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar roles", description = "Obtiene todos los roles disponibles.")
    fun getAllRoles(): ResponseEntity<List<Rol>> {
        return ResponseEntity.ok(rolService.findAll())
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear rol", description = "Crea un nuevo rol.")
    fun createRol(@RequestBody rol: Rol): ResponseEntity<Rol> {
        val nuevoRol = rolService.save(rol)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol existente.")
    fun deleteRol(@PathVariable id: Long): ResponseEntity<Void> {
        rolService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}