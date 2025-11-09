package com.example.backend.dto

data class DetallePedidoResponse(
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double
)