package com.example.backend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

// Esto es lo que la app Android enviará en el JSON para registrarse
data class RegistroRequest(
    @field:NotBlank(message = "Username no puede estar vacío")
    val username: String,

    @field:Email(message = "Email debe ser válido")
    @field:NotBlank(message = "Email no puede estar vacío")
    val email: String,

    @field:NotBlank(message = "Contraseña no puede estar vacía")
    @field:Size(min = 6, message = "Contraseña debe tener al menos 6 caracteres")
    val password: String
)