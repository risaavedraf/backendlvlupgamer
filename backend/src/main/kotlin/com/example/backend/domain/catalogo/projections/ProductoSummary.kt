package com.example.backend.domain.catalogo.projections

import java.time.LocalDateTime

interface ProductoSummary {
    val id: Long
    val nombre: String
    val descripcion: String
    val precio: Double
    val stock: Int
    val categoria: CategoriaSummary
    val imagenes: List<ImagenSummary>
    val createdAt: LocalDateTime?
    val updatedAt: LocalDateTime?
}

interface CategoriaSummary {
    val id: Long
    val nombre: String
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
}

interface ImagenSummary {
    val id: Long
    val isPrincipal: Boolean
    // NOTA: No incluimos 'data' ni 'filename' ni 'contentType' para evitar cargar el BLOB
}
