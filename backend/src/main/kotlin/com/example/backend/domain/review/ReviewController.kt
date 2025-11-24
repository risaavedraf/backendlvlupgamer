package com.example.backend.domain.review

import com.example.backend.dto.ReviewRequest
import com.example.backend.dto.ReviewResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/productos/{productoId}/reviews")
@Tag(name = "Reseñas", description = "Endpoints para gestión de reseñas de productos")
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping
    @Operation(summary = "Listar reseñas", description = "Obtiene todas las reseñas de un producto.")
    fun getReviewsForProducto(@PathVariable productoId: Long): ResponseEntity<List<ReviewResponse>> {
        val reviews = reviewService.findByProductoId(productoId)
        return ResponseEntity.ok(reviews)
    }

    @PostMapping
    @Operation(summary = "Crear reseña", description = "Agrega una nueva reseña a un producto.")
    fun createReview(
        @PathVariable productoId: Long,
        @Valid @RequestBody request: ReviewRequest,
        principal: Principal
    ): ResponseEntity<ReviewResponse> {
        val nuevaReview = reviewService.createReview(productoId, principal.name, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReview)
    }
}