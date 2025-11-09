package com.example.backend.domain.catalogo

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "productos")
data class Producto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val nombre: String,

    @Lob
    @Column(nullable = false)
    val descripcion: String,

    @Column(nullable = false)
    val precio: Double,

    @Column(nullable = false)
    val stock: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonBackReference
    var categoria: Categoria
)