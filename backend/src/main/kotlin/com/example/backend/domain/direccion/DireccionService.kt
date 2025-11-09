package com.example.backend.domain.direccion

import com.example.backend.domain.usuario.UsuarioRepository
import com.example.backend.dto.DireccionRequest
import com.example.backend.dto.DireccionResponse
import com.example.backend.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DireccionService(
    private val direccionRepository: DireccionRepository,
    private val usuarioRepository: UsuarioRepository
) {

    fun findAllByUsuario(userEmail: String): List<DireccionResponse> {
        return direccionRepository.findByUsuarioEmail(userEmail).map { it.toResponse() }
    }

    @Transactional
    fun save(request: DireccionRequest, userEmail: String): DireccionResponse {
        val usuario = usuarioRepository.findByEmail(userEmail)
            .orElseThrow { ResourceNotFoundException("Usuario no encontrado con email $userEmail") }

        // Usamos el operador !! porque @Valid en el controller nos garantiza que no son nulos
        val direccion = Direccion(
            nombre = request.nombre!!,
            nombreDestinatario = request.nombreDestinatario!!,
            calle = request.calle!!,
            numeroCasa = request.numeroCasa!!,
            numeroDepartamento = request.numeroDepartamento, // Este es nulable
            comuna = request.comuna!!,
            ciudad = request.ciudad!!,
            region = request.region!!,
            codigoPostal = request.codigoPostal!!,
            usuario = usuario
        )

        val savedDireccion = direccionRepository.save(direccion)
        return savedDireccion.toResponse()
    }

    @Transactional
    fun update(id: Long, request: DireccionRequest, userEmail: String): DireccionResponse {
        val direccion = direccionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Dirección no encontrada con id $id") }

        if (direccion.usuario.email != userEmail) {
            throw ResourceNotFoundException("Dirección no encontrada con id $id")
        }

        // Usamos el operador !! porque @Valid en el controller nos garantiza que no son nulos
        direccion.apply {
            nombre = request.nombre!!
            nombreDestinatario = request.nombreDestinatario!!
            calle = request.calle!!
            numeroCasa = request.numeroCasa!!
            numeroDepartamento = request.numeroDepartamento // Este es nulable
            comuna = request.comuna!!
            ciudad = request.ciudad!!
            region = request.region!!
            codigoPostal = request.codigoPostal!!
        }

        val updatedDireccion = direccionRepository.save(direccion)
        return updatedDireccion.toResponse()
    }

    @Transactional
    fun delete(id: Long, userEmail: String) {
        val direccion = direccionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Dirección no encontrada con id $id") }

        if (direccion.usuario.email != userEmail) {
            throw ResourceNotFoundException("Dirección no encontrada con id $id")
        }

        direccionRepository.delete(direccion)
    }
}

fun Direccion.toResponse(): DireccionResponse {
    return DireccionResponse(
        id = this.id!!,
        nombre = this.nombre,
        nombreDestinatario = this.nombreDestinatario,
        calle = this.calle,
        numeroCasa = this.numeroCasa,
        numeroDepartamento = this.numeroDepartamento,
        comuna = this.comuna,
        ciudad = this.ciudad,
        region = this.region,
        codigoPostal = this.codigoPostal
    )
}