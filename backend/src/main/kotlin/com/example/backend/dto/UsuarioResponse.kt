package com.example.backend.dto

import java.time.LocalDate

data class UsuarioResponse(
    val id: Long,
    val username: String,
    val email: String,
    val roles: String,

    // --- CAMPOS CORREGIDOS ---
    val name: String,
    val lastName: String,
    val birthDate: LocalDate?, // Jackson (la librería de JSON) convertirá esto a String
    val profileImageBase64: String? = null
)