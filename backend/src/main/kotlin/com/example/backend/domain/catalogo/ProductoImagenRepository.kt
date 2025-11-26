package com.example.backend.domain.catalogo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ProductoImagenRepository : JpaRepository<ProductoImagen, Long> {
    fun findByProductoIdAndIsPrincipalTrue(productoId: Long): Optional<ProductoImagen>
    fun findFirstByProductoIdOrderByIdAsc(productoId: Long): Optional<ProductoImagen>

    @Query("SELECT new com.example.backend.dto.ImagenLiteDTO(pi.producto.id, pi.id) FROM ProductoImagen pi WHERE pi.producto.id IN :productoIds AND pi.isPrincipal = true")
    fun findPrincipalesByProductoIdIn(productoIds: List<Long>): List<com.example.backend.dto.ImagenLiteDTO>

    @Modifying
    @Query("UPDATE ProductoImagen pi SET pi.isPrincipal = false WHERE pi.producto.id = :productoId AND pi.isPrincipal = true")
    fun resetPrincipalImage(productoId: Long)
}