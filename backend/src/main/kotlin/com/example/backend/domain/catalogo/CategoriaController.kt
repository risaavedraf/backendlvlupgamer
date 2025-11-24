package com.example.backend.domain.catalogo

import com.example.backend.dto.CategoriaResponse
import com.example.backend.dto.PageResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Catálogo - Categorías", description = "Endpoints públicos para consultar categorías")
class CategoriaController(private val categoriaService: CategoriaService) {

    @GetMapping
    @Operation(summary = "Listar categorías", description = "Obtiene una lista paginada de categorías disponibles.")
    @Parameters(
        Parameter(name = "page", description = "Número de página (0..N)", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "0")),
        Parameter(name = "size", description = "Tamaño de página", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "10")),
        Parameter(name = "sort", description = "Criterio de ordenamiento (ej. nombre,asc)", `in` = ParameterIn.QUERY, schema = Schema(type = "string", defaultValue = "nombre,asc"))
    )
    fun getAllCategorias(
        @RequestParam(required = false) query: String?, // Añadir parámetro de query para filtrado
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = ["nombre"]) pageable: Pageable // Añadir paginación
    ): ResponseEntity<PageResponse<CategoriaResponse>> {
        return if (!query.isNullOrBlank()) {
            ResponseEntity.ok(categoriaService.searchCategorias(query, pageable))
        } else {
            ResponseEntity.ok(categoriaService.findAll(pageable))
        }
    }
}
