package com.example.backend.domain.catalogo

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/productos/{productoId}/imagenes")
@Tag(name = "Imágenes de Producto", description = "Endpoints para gestión de imágenes de productos")
class ProductoImagenController(
    private val imagenService: ProductoImagenService
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Subir imagen de producto", description = "Sube una imagen para un producto (Requiere rol ADMIN).")
    fun uploadImage(
        @PathVariable productoId: Long,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("isPrincipal", defaultValue = "false") isPrincipal: Boolean
    ): ResponseEntity<Any> {
        imagenService.saveImage(productoId, file, isPrincipal)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{imageId}/base64")
    @Operation(summary = "Obtener imagen en Base64", description = "Obtiene una imagen de producto en formato Base64.")
    fun getImageAsBase64(
        @PathVariable productoId: Long,
        @PathVariable imageId: Long
    ): ResponseEntity<String> {
        val base64Image = imagenService.getImageBase64(productoId, imageId)
        return ResponseEntity.ok(base64Image)
    }

    @GetMapping("/{imageId}/download")
    @Operation(summary = "Descargar imagen", description = "Descarga el archivo de imagen de un producto.")
    fun downloadImage(
        @PathVariable productoId: Long,
        @PathVariable imageId: Long
    ): ResponseEntity<ByteArray> {
        val (data, contentType) = imagenService.getImageData(productoId, imageId)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, contentType)
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image\"")
            .body(data)
    }
}