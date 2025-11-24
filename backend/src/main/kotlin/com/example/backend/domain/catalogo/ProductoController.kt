package com.example.backend.domain.catalogo

import com.example.backend.dto.PageResponse
import com.example.backend.dto.ProductoResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Catálogo - Productos", description = "Endpoints públicos para consultar productos")
class ProductoController(private val productoService: ProductoService) {

    @GetMapping
    @Operation(summary = "Buscar productos", description = "Busca productos con filtros opcionales de texto y categoría.")
    @Parameters(
        Parameter(name = "page", description = "Número de página (0..N)", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "0")),
        Parameter(name = "size", description = "Tamaño de página", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "10")),
        Parameter(name = "sort", description = "Criterio de ordenamiento (ej. nombre,asc)", `in` = ParameterIn.QUERY, schema = Schema(type = "string", defaultValue = "nombre,asc"))
    )
    fun searchProductos(
        @RequestParam(required = false) query: String?,
        @RequestParam(required = false) categoriaId: Long?,
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = ["nombre"]) pageable: Pageable
    ): ResponseEntity<PageResponse<ProductoResponse>> {
        return ResponseEntity.ok(productoService.search(pageable, query, categoriaId))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene los detalles de un producto específico.")
    fun getProductoById(@PathVariable id: Long): ResponseEntity<ProductoResponse> {
        return ResponseEntity.ok(productoService.findById(id))
    }
}
