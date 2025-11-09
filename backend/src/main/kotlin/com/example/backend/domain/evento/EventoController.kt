package com.example.backend.domain.evento

import com.example.backend.dto.EventoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/eventos")
class EventoController(private val eventoService: EventoService) {

    @GetMapping
    fun getAllEventos(): ResponseEntity<List<EventoResponse>> {
        return ResponseEntity.ok(eventoService.findAll())
    }

    @GetMapping("/{id}")
    fun getEventoById(@PathVariable id: Long): ResponseEntity<EventoResponse> {
        return ResponseEntity.ok(eventoService.findById(id))
    }
}