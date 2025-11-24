package com.example.backend.domain.catalogo

import com.example.backend.dto.CategoriaResponse
import com.example.backend.dto.CreateCategoriaRequest
import com.example.backend.dto.PageResponse
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
@RequestMapping("/api/admin/categorias")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
@Tag(name = "Administración de Categorías", description = "Endpoints para gestión de categorías por administradores")
class AdminCategoriaController(private val categoriaService: CategoriaService) {

    @GetMapping // Nuevo endpoint para listar todas las categorías con paginación y filtrado
    @Operation(summary = "Listar categorías (Admin)", description = "Obtiene una lista paginada de categorías con opción de filtrado.")
    @Parameters(
        Parameter(name = "page", description = "Número de página (0..N)", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "0")),
        Parameter(name = "size", description = "Tamaño de página", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "10")),
        Parameter(name = "sort", description = "Criterio de ordenamiento (ej. nombre,asc)", `in` = ParameterIn.QUERY, schema = Schema(type = "string", defaultValue = "nombre,asc"))
    )
    fun getAllCategorias(
        @RequestParam(required = false) query: String?,
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = ["nombre"]) pageable: Pageable
    ): ResponseEntity<PageResponse<CategoriaResponse>> {
        return if (!query.isNullOrBlank()) {
            ResponseEntity.ok(categoriaService.searchCategorias(query, pageable))
        } else {
            ResponseEntity.ok(categoriaService.findAll(pageable))
        }
    }

    @PostMapping
    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría.")
    fun createCategoria(@Valid @RequestBody request: CreateCategoriaRequest): ResponseEntity<CategoriaResponse> {
        val nuevaCategoria = categoriaService.createCategoria(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria)
    }
}
