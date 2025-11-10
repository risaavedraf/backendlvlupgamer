package com.example.backend.domain.catalogo

import com.example.backend.dto.CategoriaResponse
import com.example.backend.dto.CreateCategoriaRequest
import com.example.backend.dto.toResponse // Importar la función central
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoriaService(private val categoriaRepository: CategoriaRepository) {

    fun findAll(): List<CategoriaResponse> {
        return categoriaRepository.findAll().map { it.toResponse() }
    }

    @Transactional
    fun createCategoria(request: CreateCategoriaRequest): CategoriaResponse {
        val nuevaCategoria = Categoria(nombre = request.nombre)
        return categoriaRepository.save(nuevaCategoria).toResponse()
    }
}

// Se elimina la función toResponse de aquí