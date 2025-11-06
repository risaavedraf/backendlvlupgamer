package com.example.backend.domain.usuario

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile

import com.example.backend.domain.usuario.UsuarioRepository


@RestController
@RequestMapping("/api/usuarios/{id}/images")
class UsuarioImagenController(
    private val imagenService: UsuarioImagenService,
    private val usuarioRepository: UsuarioRepository
) {

    @PostMapping
    fun upload(
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("profile", required = false, defaultValue = "false") profile: Boolean
    ): ResponseEntity<UsuarioImagen> {
        authorizeUserAccess(id)
        val saved = imagenService.saveImage(id, file, profile)
        return ResponseEntity.ok(saved)
    }

    @GetMapping
    fun list(@PathVariable id: Long): List<UsuarioImagen> {
        authorizeUserAccess(id)
        return imagenService.listImages(id)
    }

    @GetMapping("/{imageId}/base64")
    fun getBase64(@PathVariable id: Long, @PathVariable imageId: Long): ResponseEntity<String> {
        authorizeUserAccess(id)
        val dataUrl = imagenService.getImageBase64(id, imageId)
        return ResponseEntity.ok(dataUrl)
    }

    private fun authorizeUserAccess(usuarioId: Long) {
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth?.principal
        val email = when (principal) {
            is org.springframework.security.core.userdetails.User -> principal.username
            is String -> principal
            else -> null
    } ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autorizado")

    val usuario = usuarioRepository.findByEmail(email).orElseThrow { ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado") }
    if (usuario.id != usuarioId) throw ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para acceder a este recurso")
    }

}
