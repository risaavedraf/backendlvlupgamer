package com.example.backend.dto

import java.time.LocalDateTime

data class PedidoResponse(
    val id: Long,
    val fechaPedido: LocalDateTime,
    val total: Double,
    val estado: String,
    val direccion: DireccionResponse, // Usamos el DTO de dirección existente
    val detalles: List<DetallePedidoResponse>
)