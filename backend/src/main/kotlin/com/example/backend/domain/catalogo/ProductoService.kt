package com.example.backend.domain.catalogo

import com.example.backend.dto.ProductoResponse
import com.example.backend.exception.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class ProductoService(private val productoRepository: ProductoRepository) {

    fun findAll(): List<ProductoResponse> {
        return productoRepository.findAll().map { it.toResponse() }
    }

    fun findById(id: Long): ProductoResponse {
        return productoRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Producto no encontrado con id $id") }
            .toResponse()
    }
}

fun Producto.toResponse(): ProductoResponse {
    return ProductoResponse(
        id = this.id!!,
        nombre = this.nombre,
        descripcion = this.descripcion,
        precio = this.precio,
        stock = this.stock,
        categoria = this.categoria.toResponse()
    )
}