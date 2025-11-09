package com.example.backend.domain.catalogo

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CategoriaRepository : JpaRepository<Categoria, Long> {
    fun findByNombre(nombre: String): Optional<Categoria>
}