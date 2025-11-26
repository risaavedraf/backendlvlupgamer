package com.example.backend.domain.catalogo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import com.example.backend.domain.catalogo.projections.ProductoSummary
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface ProductoRepository : JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
    fun findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(nombre: String, descripcion: String): List<Producto>
}
