package com.example.backend.domain.usuario

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

// @Entity le dice a JPA que esta clase es una tabla en la base de datos.
// El plugin 'jpa' de Kotlin se encarga de crear el constructor sin argumentos.
@Entity
@Table(name = "usuarios") // Nombra la tabla en plural
data class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // El ID es autogenerado por la BD

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    var username: String,

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un formato de email válido")
    var email: String,

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    var passwordHash: String, // NUNCA guardamos la contraseña en texto plano

    // Podríamos añadir roles (ADMIN, USER) aquí más adelante
    var roles: String = "USER" 
)