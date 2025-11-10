package com.example.backend.domain.evento

import com.example.backend.dto.CreateEventoRequest
import com.example.backend.dto.EventoResponse
import com.example.backend.dto.UpdateEventoRequest
import com.example.backend.dto.toResponse
import com.example.backend.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    fun createEvento(request: CreateEventoRequest): EventoResponse {
        val nuevoEvento = Evento(
            name = request.name,
            description = request.description,
            date = request.date,
            locationName = request.locationName,
            latitude = request.latitude,
            longitude = request.longitude
        )
        return eventoRepository.save(nuevoEvento).let { toResponseWithImage(it) }
    }

    @Transactional
    fun updateEvento(id: Long, request: UpdateEventoRequest): EventoResponse {
        val evento = eventoRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Evento no encontrado con ID $id") }

        request.name?.let { evento.name = it }
        request.description?.let { evento.description = it }
        request.date?.let { evento.date = it }
        request.locationName?.let { evento.locationName = it }
        request.latitude?.let { evento.latitude = it }
        request.longitude?.let { evento.longitude = it }

        return eventoRepository.save(evento).let { toResponseWithImage(it) }
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
