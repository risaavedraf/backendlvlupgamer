package com.example.backend.dto

// Esto es lo que el backend devolverá a la app tras un registro o login exitoso
data class UsuarioResponse(
    val id: Long,
    val username: String,
    val email: String,
    val roles: String
)