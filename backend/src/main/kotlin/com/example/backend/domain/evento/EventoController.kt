package com.example.backend.domain.evento

import com.example.backend.dto.EventoResponse
import com.example.backend.dto.PageResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/eventos")
@Tag(name = "Eventos", description = "Endpoints públicos para consultar eventos")
class EventoController(private val eventoService: EventoService) {

    @GetMapping
    @Operation(summary = "Listar eventos", description = "Obtiene una lista paginada de eventos disponibles.")
    @Parameters(
        Parameter(name = "page", description = "Número de página (0..N)", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "0")),
        Parameter(name = "size", description = "Tamaño de página", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "10")),
        Parameter(name = "sort", description = "Criterio de ordenamiento (ej. date,asc)", `in` = ParameterIn.QUERY, schema = Schema(type = "string", defaultValue = "date,asc"))
    )
    fun getAllEventos(
        @RequestParam(required = false) query: String?, // Añadir parámetro de query para filtrado
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = ["date"]) pageable: Pageable // Añadir paginación
    ): ResponseEntity<PageResponse<EventoResponse>> {
        return if (!query.isNullOrBlank()) {
            ResponseEntity.ok(eventoService.searchEventos(query, pageable))
        } else {
            ResponseEntity.ok(eventoService.findAll(pageable))
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evento por ID", description = "Obtiene los detalles de un evento específico.")
    fun getEventoById(@PathVariable id: Long): ResponseEntity<EventoResponse> {
        return ResponseEntity.ok(eventoService.findById(id))
    }
}
