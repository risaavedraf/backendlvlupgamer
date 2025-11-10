package com.example.backend.domain.catalogo

import com.example.backend.dto.CategoriaResponse
import com.example.backend.dto.CreateCategoriaRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/categorias")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
class AdminCategoriaController(private val categoriaService: CategoriaService) {

    @PostMapping
    fun createCategoria(@Valid @RequestBody request: CreateCategoriaRequest): ResponseEntity<CategoriaResponse> {
        val nuevaCategoria = categoriaService.createCategoria(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria)
    }
}
