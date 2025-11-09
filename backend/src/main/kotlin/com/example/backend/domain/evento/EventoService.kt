package com.example.backend.domain.evento

import com.example.backend.dto.EventoResponse
import com.example.backend.dto.toResponse
import com.example.backend.exception.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class EventoService(
    private val eventoRepository: EventoRepository,
    private val eventoImagenRepository: EventoImagenRepository
) {

    fun findAll(): List<EventoResponse> {
        return eventoRepository.findAll().map { toResponseWithImage(it) }
    }

    fun findById(id: Long): EventoResponse {
        val evento = eventoRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Evento no encontrado con id $id") }
        return toResponseWithImage(evento)
    }

    private fun toResponseWithImage(evento: Evento): EventoResponse {
        val imagenPrincipal = eventoImagenRepository.findByEventoIdAndIsPrincipalTrue(evento.id!!)
        val imageUrl = imagenPrincipal.map { "/api/eventos/${evento.id}/imagenes/${it.id!!}/base64" }.orElse(null)

        return evento.toResponse(imageUrl)
    }
}

fun Evento.toResponse(imageUrl: String? = null): EventoResponse {
    return EventoResponse(
        id = this.id!!,
        name = this.name,
        description = this.description,
        date = this.date,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude,
        imageUrl = imageUrl
    )
}