package com.example.backend.domain.usuario

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate // Importante

@Entity
@Table(name = "usuarios")
data class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    var username: String,

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un formato de email válido")
    var email: String,

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    var passwordHash: String,

    // --- CAMPOS CORREGIDOS (BASADOS EN TU User.kt) ---
    @Column(nullable = false)
    @NotBlank
    var name: String, // Reemplaza fullName

    @Column(nullable = false)
    @NotBlank
    var lastName: String, // Reemplaza fullName

    @Column(nullable = true) // Hacemos la fecha de nacimiento opcional
    var birthDate: LocalDate? = null,
    // --- FIN CAMPOS CORREGIDOS ---

    var roles: String = "USER"

    // El campo 'address' que había añadido antes estaba incorrecto, lo quitamos.
)