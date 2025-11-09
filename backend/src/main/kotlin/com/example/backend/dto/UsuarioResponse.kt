package com.example.backend.dto

import java.time.LocalDate

data class UsuarioResponse(
    val id: Long,
    val username: String,
    val email: String,
    val roles: Set<String>, // <-- CORREGIDO: de String a Set<String>

    val name: String,
    val lastName: String,
    val birthDate: LocalDate?,
    val profileImageBase64: String? = null
)