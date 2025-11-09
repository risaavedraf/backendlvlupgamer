package com.example.backend.domain.catalogo

import com.example.backend.dto.ProductoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.* // Importar RequestParam

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

    @GetMapping("/search")
    fun searchProductos(@RequestParam query: String): ResponseEntity<List<ProductoResponse>> {
        return ResponseEntity.ok(productoService.searchProductos(query))
    }
}
