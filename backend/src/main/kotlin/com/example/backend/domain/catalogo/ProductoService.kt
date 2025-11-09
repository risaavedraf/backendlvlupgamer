package com.example.backend.domain.catalogo

import com.example.backend.dto.ProductoResponse
import com.example.backend.dto.toResponse // Importar la función central
import com.example.backend.exception.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class ProductoService(
    private val productoRepository: ProductoRepository,
    private val imagenRepository: ProductoImagenRepository
) {

    fun findAll(): List<ProductoResponse> {
        return productoRepository.findAll().map { toResponseWithImage(it) }
    }

    fun findById(id: Long): ProductoResponse {
        val producto = productoRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Producto no encontrado con id $id") }
        return toResponseWithImage(producto)
    }

    private fun toResponseWithImage(producto: Producto): ProductoResponse {
        val imagenPrincipal = imagenRepository.findByProductoIdAndIsPrincipalTrue(producto.id!!)
        val imagenUrl = imagenPrincipal.map { "/api/productos/${producto.id}/imagenes/${it.id!!}/base64" }.orElse(null)
        
        // Llamamos a la función centralizada y le pasamos la URL
        return producto.toResponse(imagenUrl)
    }
}

// Se eliminan las funciones toResponse de aquí