package com.example.backend.domain.catalogo

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object ProductoSpecification {

    fun withSearchQuery(query: String): Specification<Producto> {
        return Specification { root, _, criteriaBuilder ->
            val lowerCaseQuery = "%${query.lowercase()}%"

            val nombreLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), lowerCaseQuery)
            val descripcionLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("descripcion")), lowerCaseQuery)

            criteriaBuilder.or(nombreLike, descripcionLike)
        }
    }
}
