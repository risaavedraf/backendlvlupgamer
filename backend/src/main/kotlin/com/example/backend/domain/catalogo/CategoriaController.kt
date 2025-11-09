package com.example.backend.domain.catalogo

import com.example.backend.dto.CategoriaResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categorias")
class CategoriaController(private val categoriaService: CategoriaService) {

    @GetMapping
    fun getAllCategorias(): ResponseEntity<List<CategoriaResponse>> {
        return ResponseEntity.ok(categoriaService.findAll())
    }
}