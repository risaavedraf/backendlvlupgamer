package com.example.backend.domain.carrito

import com.example.backend.domain.carrito.dto.AddItemRequest
import com.example.backend.domain.carrito.dto.CarritoResponse
import com.example.backend.domain.carrito.dto.CuponRequest
import com.example.backend.domain.carrito.dto.UpdateQuantityRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito de Compras", description = "Endpoints para gestión del carrito de compras")
class CarritoController(private val carritoService: CarritoService) {

    @GetMapping
    @Operation(summary = "Obtener carrito", description = "Obtiene el carrito de compras actual del usuario.")
    fun getCarrito(): ResponseEntity<CarritoResponse> {
        val carrito = carritoService.getCarritoResponse()
        return ResponseEntity.ok(carrito)
    }

    @PostMapping("/items")
    @Operation(summary = "Agregar ítem", description = "Agrega un producto al carrito.")
    fun addItemToCarrito(@RequestBody request: AddItemRequest): ResponseEntity<CarritoResponse> {
        val carritoActualizado = carritoService.addItem(request)
        return ResponseEntity.ok(carritoActualizado)
    }

    @PutMapping("/items/{productoId}")
    @Operation(summary = "Actualizar cantidad", description = "Actualiza la cantidad de un producto en el carrito.")
    fun updateItemQuantity(
        @PathVariable productoId: Long,
        @RequestBody request: UpdateQuantityRequest
    ): ResponseEntity<CarritoResponse> {
        val carritoActualizado = carritoService.updateItemQuantity(productoId, request)
        return ResponseEntity.ok(carritoActualizado)
    }

    @DeleteMapping("/items/{productoId}")
    @Operation(summary = "Eliminar ítem", description = "Elimina un producto del carrito.")
    fun removeItemFromCarrito(@PathVariable productoId: Long): ResponseEntity<CarritoResponse> {
        val carritoActualizado = carritoService.removeItem(productoId)
        return ResponseEntity.ok(carritoActualizado)
    }

    @DeleteMapping
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los productos del carrito.")
    fun clearCarrito(): ResponseEntity<Void> {
        carritoService.clearCarrito()
        return ResponseEntity.noContent().build()
    }

    // --- Nuevos Endpoints para Cupones ---

    @PostMapping("/cupon")
    @Operation(summary = "Aplicar cupón", description = "Aplica un cupón de descuento al carrito.")
    fun aplicarCupon(@RequestBody cuponRequest: CuponRequest): ResponseEntity<CarritoResponse> {
        val carritoActualizado = carritoService.aplicarCupon(cuponRequest.codigo)
        return ResponseEntity.ok(carritoActualizado)
    }

    @DeleteMapping("/cupon")
    @Operation(summary = "Quitar cupón", description = "Elimina el cupón aplicado al carrito.")
    fun quitarCupon(): ResponseEntity<CarritoResponse> {
        val carritoActualizado = carritoService.quitarCupon()
        return ResponseEntity.ok(carritoActualizado)
    }
}
