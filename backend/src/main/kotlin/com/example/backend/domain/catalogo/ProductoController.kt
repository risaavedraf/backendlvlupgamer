package com.example.backend.domain.catalogo

import com.example.backend.dto.ProductoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/productos")
class ProductoController(private val productoService: ProductoService) {

    @GetMapping
    fun getAllProductos(): ResponseEntity<List<ProductoResponse>> {
        return ResponseEntity.ok(productoService.findAll())
    }

    @GetMapping("/{id}")
    fun getProductoById(@PathVariable id: Long): ResponseEntity<ProductoResponse> {
        return ResponseEntity.ok(productoService.findById(id))
    }
}