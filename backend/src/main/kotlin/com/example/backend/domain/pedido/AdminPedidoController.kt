package com.example.backend.domain.pedido

import com.example.backend.dto.PedidoResponse
import com.example.backend.dto.UpdatePedidoEstadoRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/pedidos")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
class AdminPedidoController(private val pedidoService: PedidoService) {

    @GetMapping
    fun getAllPedidos(): ResponseEntity<List<PedidoResponse>> {
        return ResponseEntity.ok(pedidoService.findAllPedidos())
    }

    @GetMapping("/{id}")
    fun getPedidoById(@PathVariable id: Long): ResponseEntity<PedidoResponse> {
        return ResponseEntity.ok(pedidoService.findPedidoById(id))
    }

    @PutMapping("/{id}/estado")
    fun updatePedidoEstado(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdatePedidoEstadoRequest
    ): ResponseEntity<PedidoResponse> {
        val pedidoActualizado = pedidoService.actualizarEstadoPedido(id, request.nuevoEstadoNombre)
        return ResponseEntity.ok(pedidoActualizado)
    }
}
