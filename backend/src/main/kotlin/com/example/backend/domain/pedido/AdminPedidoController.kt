package com.example.backend.domain.pedido

import com.example.backend.dto.PageResponse
import com.example.backend.dto.PedidoResponse
import com.example.backend.dto.UpdatePedidoEstadoRequest
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/pedidos")
@PreAuthorize("hasRole('ADMIN')") // Asegura que todos los métodos en este controlador requieren el rol ADMIN
@Tag(name = "Administración de Pedidos", description = "Endpoints para gestión de pedidos por administradores")
class AdminPedidoController(private val pedidoService: PedidoService) {

    @GetMapping
    @Operation(summary = "Listar pedidos (Admin)", description = "Obtiene una lista paginada de todos los pedidos.")
    @Parameters(
        Parameter(name = "page", description = "Número de página (0..N)", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "0")),
        Parameter(name = "size", description = "Tamaño de página", `in` = ParameterIn.QUERY, schema = Schema(type = "integer", defaultValue = "10")),
        Parameter(name = "sort", description = "Criterio de ordenamiento (ej. fechaPedido,asc)", `in` = ParameterIn.QUERY, schema = Schema(type = "string", defaultValue = "fechaPedido,asc"))
    )
    fun getAllPedidos(
        @RequestParam(required = false) estado: String?,
        @Parameter(hidden = true) @PageableDefault(size = 10, sort = ["fechaPedido"]) pageable: Pageable
    ): ResponseEntity<PageResponse<PedidoResponse>> {
        return ResponseEntity.ok(pedidoService.findAllPedidos(pageable, estado))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID (Admin)", description = "Obtiene los detalles de un pedido específico.")
    fun getPedidoById(@PathVariable id: Long): ResponseEntity<PedidoResponse> {
        return ResponseEntity.ok(pedidoService.findPedidoById(id))
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de pedido", description = "Actualiza el estado de un pedido (ej. EN_PROCESO, ENVIADO).")
    fun updatePedidoEstado(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdatePedidoEstadoRequest
    ): ResponseEntity<PedidoResponse> {
        val pedidoActualizado = pedidoService.actualizarEstadoPedido(id, request.nuevoEstadoNombre)
        return ResponseEntity.ok(pedidoActualizado)
    }
}
