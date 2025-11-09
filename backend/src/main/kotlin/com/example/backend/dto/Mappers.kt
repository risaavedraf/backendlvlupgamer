package com.example.backend.dto

import com.example.backend.domain.catalogo.Categoria
import com.example.backend.domain.catalogo.Producto
import com.example.backend.domain.direccion.Direccion
import com.example.backend.domain.review.Review
import com.example.backend.domain.usuario.Usuario

// --- Mappers de Catálogo ---

fun Categoria.toResponse(): CategoriaResponse {
    return CategoriaResponse(
        id = this.id!!,
        nombre = this.nombre
    )
}

fun Producto.toResponse(imagenUrl: String? = null): ProductoResponse {
    return ProductoResponse(
        id = this.id!!,
        nombre = this.nombre,
        descripcion = this.descripcion,
        precio = this.precio,
        stock = this.stock,
        categoria = this.categoria.toResponse(),
        imagenUrl = imagenUrl
    )
}

// --- Mapper de Review ---

fun Review.toResponse(): ReviewResponse {
    return ReviewResponse(
        id = this.id!!,
        calificacion = this.calificacion,
        comentario = this.comentario,
        fecha = this.fecha,
        author = this.usuario.username,
        authorId = this.usuario.id!!
    )
}

// --- Mappers de Usuario y Dirección ---

fun Usuario.toResponse(profileImageBase64: String? = null): UsuarioResponse {
    return UsuarioResponse(
        id = this.id!!,
        username = this.username,
        email = this.email,
        roles = this.roles.map { it.nombre }.toSet(),
        name = this.name,
        lastName = this.lastName,
        birthDate = this.birthDate,
        profileImageBase64 = profileImageBase64
    )
}

fun Direccion.toResponse(): DireccionResponse {
    return DireccionResponse(
        id = this.id!!,
        nombre = this.nombre,
        nombreDestinatario = this.nombreDestinatario,
        calle = this.calle,
        numeroCasa = this.numeroCasa,
        numeroDepartamento = this.numeroDepartamento,
        comuna = this.comuna,
        ciudad = this.ciudad,
        region = this.region,
        codigoPostal = this.codigoPostal
    )
}