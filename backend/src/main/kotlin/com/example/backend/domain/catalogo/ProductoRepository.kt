package com.example.backend.domain.catalogo

import org.springframework.data.jpa.repository.JpaRepository

interface ProductoRepository : JpaRepository<Producto, Long> {
}