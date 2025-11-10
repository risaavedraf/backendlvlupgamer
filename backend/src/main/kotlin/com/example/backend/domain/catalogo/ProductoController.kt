package com.example.backend.domain.catalogo

import com.example.backend.dto.PageResponse
import com.example.backend.dto.ProductoResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault // Importar PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/productos")
class ProductoController(private val productoService: ProductoService) {

    @GetMapping
    fun getAllProductos(
        @PageableDefault(size = 10, sort = ["nombre"]) pageable: Pageable // Añadir paginación
    ): ResponseEntity<PageResponse<ProductoResponse>> {
        return ResponseEntity.ok(productoService.findAll(pageable))
    }

    @GetMapping("/{id}")
    fun getProductoById(@PathVariable id: Long): ResponseEntity<ProductoResponse> {
        return ResponseEntity.ok(productoService.findById(id))
    }

    @GetMapping("/search")
    fun searchProductos(
        @RequestParam query: String,
        @PageableDefault(size = 10, sort = ["nombre"]) pageable: Pageable // Añadir paginación
    ): ResponseEntity<PageResponse<ProductoResponse>> {
        return ResponseEntity.ok(productoService.searchProductos(query, pageable))
    }
}
