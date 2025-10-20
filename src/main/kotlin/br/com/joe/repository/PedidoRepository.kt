package br.com.joe.repository

import br.com.joe.entity.Pedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PedidoRepository: JpaRepository<Pedido, Long> {
    fun existsByNumeroPedido(numeroPedido: String): Boolean

    @Query(
        value = """
        SELECT * FROM pedido p 
        WHERE REPLACE(JSON_UNQUOTE(JSON_EXTRACT(p.user_json, '$.cpf')), '.', '') = :cpf
    """,
        nativeQuery = true
    )
    fun findAllByCpf(cpf: String): List<Pedido>
    fun findByNumeroPedido(numeroPedido: String): Pedido?
}