package com.example.backend.domain.catalogo

import com.example.backend.dto.CategoriaResponse
import com.example.backend.dto.toResponse // Importar la función central
import org.springframework.stereotype.Service

@Service
class CategoriaService(private val categoriaRepository: CategoriaRepository) {

    fun findAll(): List<CategoriaResponse> {
        return categoriaRepository.findAll().map { it.toResponse() }
    }
}

// Se elimina la función toResponse de aquí