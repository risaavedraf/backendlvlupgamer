package com.example.backend.domain.usuario

import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import java.time.OffsetDateTime
import java.util.Base64

@Service
class UsuarioImagenService(
    private val usuarioRepository: UsuarioRepository,
    private val imagenRepo: UsuarioImagenRepository
) {

    fun saveImage(usuarioId: Long, file: MultipartFile, profile: Boolean = false): UsuarioImagen {
    val usuario = usuarioRepository.findById(usuarioId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado") }

    val bytes = try { file.bytes } catch (ex: Exception) { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al leer el archivo") }

    // Validaciones
    val maxBytes = 5_242_880L // 5 MB
    if (bytes.size.toLong() > maxBytes) throw ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Archivo demasiado grande (max 5MB)")

    val allowed = setOf("image/png", "image/jpeg", "image/jpg", "image/webp")
    val ct = file.contentType ?: ""
    if (!allowed.contains(ct.lowercase())) throw ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Tipo de archivo no permitido")

        if (profile) {
            imagenRepo.findByUsuarioId(usuarioId).forEach { if (it.profile) { it.profile = false; imagenRepo.save(it) } }
        }

        val img = UsuarioImagen(
            filename = file.originalFilename ?: "image",
            contentType = file.contentType ?: "application/octet-stream",
            data = bytes,
            size = bytes.size.toLong(),
            uploadedAt = OffsetDateTime.now(),
            profile = profile,
            usuario = usuario
        )

    return imagenRepo.save(img)
    }

    fun getImageBase64(usuarioId: Long, imageId: Long): String {
    val img = imagenRepo.findById(imageId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada") }
    if (img.usuario?.id != usuarioId) throw ResponseStatusException(HttpStatus.FORBIDDEN, "Imagen no pertenece al usuario")
        val encoded = Base64.getEncoder().encodeToString(img.data)
        return "data:${img.contentType};base64,$encoded"
    }

    fun listImages(usuarioId: Long): List<UsuarioImagen> = imagenRepo.findByUsuarioId(usuarioId)

}
