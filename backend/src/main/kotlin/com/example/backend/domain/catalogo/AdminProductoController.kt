package com.example.backend.domain.catalogo

import com.example.backend.dto.CreateProductoRequest
import com.example.backend.dto.PageResponse
import com.example.backend.dto.ProductoResponse
import com.example.backend.dto.UpdateProductoRequest
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/productos")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
@Tag(name = "Administración de Productos", description = "Endpoints para gestión de productos por administradores")
class AdminProductoController(private val productoService: ProductoService) {

    @GetMapping
    @Operation(summary = "Listar productos (Admin)", description = "Obtiene una lista paginada de productos con opciones de filtrado.")
    @Parameters(
        Parameter(name = "page", description = "Número de página (0..N)", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "0")),
        Parameter(name = "size", description = "Tamaño de página", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "10")),
        Parameter(name = "sort", description = "Criterio de ordenamiento (ej. nombre,asc)", `in` = ParameterIn.QUERY, schema = Schema(type = "string", defaultValue = "nombre,asc"))
    )
    fun getAllProductos(
        @RequestParam(required = false) query: String?,
        @RequestParam(required = false) categoriaId: Long?,
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = ["nombre"]) pageable: Pageable
    ): ResponseEntity<PageResponse<ProductoResponse>> {
        return ResponseEntity.ok(productoService.search(pageable, query, categoriaId))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID (Admin)", description = "Obtiene los detalles de un producto específico.")
    fun getProductoById(@PathVariable id: Long): ResponseEntity<ProductoResponse> {
        return ResponseEntity.ok(productoService.findById(id))
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto.")
    fun createProducto(@Valid @RequestBody request: CreateProductoRequest): ResponseEntity<ProductoResponse> {
        val nuevoProducto = productoService.createProducto(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza la información de un producto existente.")
    fun updateProducto(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateProductoRequest
    ): ResponseEntity<ProductoResponse> {
        val productoActualizado = productoService.updateProducto(id, request)
        return ResponseEntity.ok(productoActualizado)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del catálogo.")
    fun deleteProducto(@PathVariable id: Long): ResponseEntity<Void> {
        productoService.deleteProducto(id)
        return ResponseEntity.noContent().build()
    }
}
