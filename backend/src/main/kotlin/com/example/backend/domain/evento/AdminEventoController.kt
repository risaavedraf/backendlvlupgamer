package com.example.backend.domain.evento

import com.example.backend.dto.CreateEventoRequest
import com.example.backend.dto.EventoResponse
import com.example.backend.dto.UpdateEventoRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/eventos")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
class AdminEventoController(private val eventoService: EventoService) {

    @PostMapping
    fun createEvento(@Valid @RequestBody request: CreateEventoRequest): ResponseEntity<EventoResponse> {
        val nuevoEvento = eventoService.createEvento(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEvento)
    }

    @PutMapping("/{id}")
    fun updateEvento(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateEventoRequest
    ): ResponseEntity<EventoResponse> {
        val eventoActualizado = eventoService.updateEvento(id, request)
        return ResponseEntity.ok(eventoActualizado)
    }
}
