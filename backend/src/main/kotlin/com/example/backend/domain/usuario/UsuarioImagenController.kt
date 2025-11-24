package com.example.backend.domain.usuario

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("/api/usuarios/{id}/images")
@Tag(name = "Imágenes de Usuario", description = "Endpoints para gestión de imágenes de perfil de usuarios")
class UsuarioImagenController(
    private val imagenService: UsuarioImagenService
    // No necesitas UsuarioRepository aquí
) {

    @PostMapping
    @Operation(summary = "Subir imagen", description = "Sube una imagen para un usuario específico.")
    fun upload(
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("profile", required = false, defaultValue = "false") profile: Boolean,
        principal: Principal // Inyecta Principal
    ): ResponseEntity<UsuarioImagen> {
        // Delega la autorización y la lógica al servicio
        val saved = imagenService.saveImage(id, file, profile, principal.name)
        return ResponseEntity.ok(saved)
    }

    @GetMapping
    @Operation(summary = "Listar imágenes", description = "Obtiene todas las imágenes de un usuario.")
    fun list(@PathVariable id: Long, principal: Principal): ResponseEntity<List<UsuarioImagen>> {
        // Delega la autorización y la lógica al servicio
        val images = imagenService.listImages(id, principal.name)
        return ResponseEntity.ok(images)
    }

    @GetMapping("/{imageId}/base64")
    @Operation(summary = "Obtener imagen en Base64", description = "Obtiene una imagen específica en formato Base64.")
    fun getBase64(@PathVariable id: Long, @PathVariable imageId: Long, principal: Principal): ResponseEntity<String> {
        // Delega la autorización y la lógica al servicio
        val dataUrl = imagenService.getImageBase64(id, imageId, principal.name)
        return ResponseEntity.ok(dataUrl)
    }
}