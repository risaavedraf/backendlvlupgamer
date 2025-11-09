package com.example.backend.domain.pedido

import org.springframework.data.jpa.repository.JpaRepository

interface PedidoRepository : JpaRepository<Pedido, Long> {
    fun findByUsuarioEmailOrderByFechaPedidoDesc(email: String): List<Pedido>
}