package com.example.backend.domain.usuario

import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioImagenRepository : JpaRepository<UsuarioImagen, Long> {
    fun findByUsuarioId(usuarioId: Long): List<UsuarioImagen>
    fun findByUsuarioIdAndProfileTrue(usuarioId: Long): UsuarioImagen?
}
