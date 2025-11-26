package com.example.backend.domain.catalogo

import com.example.backend.dto.CreateProductoRequest
import com.example.backend.dto.PageResponse
import com.example.backend.dto.ProductoResponse
import com.example.backend.dto.UpdateProductoRequest
import com.example.backend.dto.toPageResponse
import com.example.backend.dto.toResponse
import com.example.backend.dto.ImagenLiteDTO
import com.example.backend.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductoService(
    private val productoRepository: ProductoRepository,
    private val imagenRepository: ProductoImagenRepository,
    private val categoriaRepository: CategoriaRepository,
    @Value("\${app.api.url:http://18.208.196.35:8080}") private val apiBaseUrl: String
) {

    // Método unificado para búsqueda y listado
    @Transactional(readOnly = true)
    fun search(pageable: Pageable, query: String?, categoriaId: Long?): PageResponse<ProductoResponse> {
        val spec = ProductoSpecification.build(query, categoriaId)
        
        // 1. Buscar Productos (Entidades) - Lazy loading de imágenes evita traer BLOBs
        val page = productoRepository.findAll(spec, pageable)
        
        // 2. Obtener IDs de productos cargados
        val productoIds = page.content.mapNotNull { it.id }
        
        // 3. Buscar solo IDs de imágenes principales en una consulta separada (Manual Assembly)
        val imagenesMap = if (productoIds.isNotEmpty()) {
            imagenRepository.findPrincipalesByProductoIdIn(productoIds)
                .associateBy { it.productoId }
        } else {
            emptyMap()
        }

        // 4. Ensamblar respuesta en memoria
        return page.toPageResponse { producto ->
            val imagenLite = imagenesMap[producto.id]
            val imagenUrl = imagenLite?.let { dto -> "$apiBaseUrl/api/productos/${producto.id}/imagenes/${dto.imagenId}/download" }
            producto.toResponse(imagenUrl)
        }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): ProductoResponse {
        val producto = productoRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Producto no encontrado con id $id") }
        return toResponseWithImage(producto)
    }

    @Transactional
    fun createProducto(request: CreateProductoRequest): ProductoResponse {
        val categoria = categoriaRepository.findById(request.categoriaId)
            .orElseThrow { ResourceNotFoundException("Categoría no encontrada con ID ${request.categoriaId}") }

        val nuevoProducto = Producto(
            nombre = request.nombre,
            descripcion = request.descripcion,
            precio = request.precio,
            stock = request.stock,
            categoria = categoria
        )
        return productoRepository.save(nuevoProducto).let { toResponseWithImage(it) }
    }

    @Transactional
    fun updateProducto(id: Long, request: UpdateProductoRequest): ProductoResponse {
        val producto = productoRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Producto no encontrado con ID $id") }

        request.nombre?.let { producto.nombre = it }
        request.descripcion?.let { producto.descripcion = it }
        request.precio?.let { producto.precio = it }
        request.stock?.let { producto.stock = it }
        request.categoriaId?.let {
            val categoria = categoriaRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Categoría no encontrada con ID $it") }
            producto.categoria = categoria
        }

        return productoRepository.save(producto).let { toResponseWithImage(it) }
    }

    @Transactional
    fun deleteProducto(id: Long) {
        if (!productoRepository.existsById(id)) {
            throw ResourceNotFoundException("Producto no encontrado con ID $id")
        }
        productoRepository.deleteById(id)
    }

    private fun toResponseWithImage(producto: Producto): ProductoResponse {
        // Fallback para operaciones individuales (create/update/getOne)
        // Aquí podríamos optimizar también, pero es menos crítico que en listados.
        // Usamos la estrategia antigua o una consulta ligera si fuera necesario.
        // Por ahora mantenemos la lógica segura de buscar la imagen principal.
        val imagenPrincipal = imagenRepository.findByProductoIdAndIsPrincipalTrue(producto.id!!)
        val imagenUrl = imagenPrincipal.map { img: ProductoImagen -> "$apiBaseUrl/api/productos/${producto.id}/imagenes/${img.id!!}/download" }.orElse(null)
        
        return producto.toResponse(imagenUrl)
    }
}
