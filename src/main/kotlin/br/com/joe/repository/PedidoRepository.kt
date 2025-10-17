package br.com.joe.repository

import br.com.joe.entity.Pedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoRepository: JpaRepository<Pedido, Long> {
    fun existsByNumeroPedido(numeroPedido: String): Boolean
}