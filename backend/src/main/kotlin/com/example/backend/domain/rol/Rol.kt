package com.example.backend.domain.rol

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Rol(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val nombre: String
)