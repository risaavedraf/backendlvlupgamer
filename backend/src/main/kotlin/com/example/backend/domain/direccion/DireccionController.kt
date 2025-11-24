package com.example.backend.domain.direccion

import com.example.backend.dto.DireccionRequest
import com.example.backend.dto.DireccionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/direcciones")
@Tag(name = "Direcciones", description = "Endpoints para gestión de direcciones de envío")
class DireccionController(private val direccionService: DireccionService) {

    @GetMapping
    @Operation(summary = "Listar mis direcciones", description = "Obtiene todas las direcciones del usuario autenticado.")
    fun getMisDirecciones(principal: Principal): ResponseEntity<List<DireccionResponse>> {
        val direcciones = direccionService.findAllByUsuario(principal.name)
        return ResponseEntity.ok(direcciones)
    }

    @GetMapping("/{id}") // Nuevo endpoint
    @Operation(summary = "Obtener dirección por ID", description = "Obtiene los detalles de una dirección específica.")
    fun getDireccionById(
        @PathVariable id: Long,
        principal: Principal
    ): ResponseEntity<DireccionResponse> {
        val direccion = direccionService.findById(id, principal.name)
        return ResponseEntity.ok(direccion)
    }

    @PostMapping
    @Operation(summary = "Crear dirección", description = "Agrega una nueva dirección para el usuario.")
    fun createDireccion(
        @Valid @RequestBody request: DireccionRequest,
        principal: Principal
    ): ResponseEntity<DireccionResponse> {
        val nuevaDireccion = direccionService.save(request, principal.name)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaDireccion)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar dirección", description = "Actualiza una dirección existente.")
    fun updateDireccion(
        @PathVariable id: Long,
        @Valid @RequestBody request: DireccionRequest,
        principal: Principal
    ): ResponseEntity<DireccionResponse> {
        val direccionActualizada = direccionService.update(id, request, principal.name)
        return ResponseEntity.ok(direccionActualizada)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar dirección", description = "Elimina una dirección del usuario.")
    fun deleteDireccion(
        @PathVariable id: Long,
        principal: Principal
    ): ResponseEntity<Void> {
        direccionService.delete(id, principal.name)
        return ResponseEntity.noContent().build()
    }
}
