package com.example.backend.domain.evento

import com.example.backend.dto.CreateEventoRequest
import com.example.backend.dto.EventoResponse
import com.example.backend.dto.PageResponse
import com.example.backend.dto.UpdateEventoRequest
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/eventos")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
@Tag(name = "Administración de Eventos", description = "Endpoints para gestión de eventos por administradores")
class AdminEventoController(private val eventoService: EventoService) {

    @GetMapping // Nuevo endpoint para listar todos los eventos con paginación y filtrado
    @Operation(summary = "Listar eventos (Admin)", description = "Obtiene una lista paginada de eventos con opción de filtrado.")
    @Parameters(
        Parameter(name = "page", description = "Número de página (0..N)", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "0")),
        Parameter(name = "size", description = "Tamaño de página", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "10")),
        Parameter(name = "sort", description = "Criterio de ordenamiento (ej. date,asc)", `in` = ParameterIn.QUERY, schema = Schema(type = "string", defaultValue = "date,asc"))
    )
    fun getAllEventos(
        @RequestParam(required = false) query: String?,
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = ["date"]) pageable: Pageable
    ): ResponseEntity<PageResponse<EventoResponse>> {
        return if (!query.isNullOrBlank()) {
            ResponseEntity.ok(eventoService.searchEventos(query, pageable))
        } else {
            ResponseEntity.ok(eventoService.findAll(pageable))
        }
    }

    @PostMapping
    @Operation(summary = "Crear evento", description = "Crea un nuevo evento.")
    fun createEvento(@Valid @RequestBody request: CreateEventoRequest): ResponseEntity<EventoResponse> {
        val nuevoEvento = eventoService.createEvento(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEvento)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evento", description = "Actualiza la información de un evento existente.")
    fun updateEvento(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateEventoRequest
    ): ResponseEntity<EventoResponse> {
        val eventoActualizado = eventoService.updateEvento(id, request)
        return ResponseEntity.ok(eventoActualizado)
    }
}
