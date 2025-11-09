package com.example.backend.domain.catalogo

import com.example.backend.dto.CategoriaResponse
import org.springframework.stereotype.Service

@Service
class CategoriaService(private val categoriaRepository: CategoriaRepository) {

    fun findAll(): List<CategoriaResponse> {
        return categoriaRepository.findAll().map { it.toResponse() }
    }
}

fun Categoria.toResponse(): CategoriaResponse {
    return CategoriaResponse(
        id = this.id!!,
        nombre = this.nombre
    )
}