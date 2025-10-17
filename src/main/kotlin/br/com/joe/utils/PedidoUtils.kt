package br.com.joe.utils

import br.com.joe.entity.vo.ProductVO
import br.com.joe.repository.PedidoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PedidoUtils {
    @Autowired
    private lateinit var pedidoRepository: PedidoRepository

    fun gerarNumeroPedidoUnico(): String {
        var numero: String
        do {
            numero = (100000..999999).random().toString()
        } while (pedidoRepository.existsByNumeroPedido(numero))
        return numero
    }

    fun calcularValorTotal(produtos: List<ProductVO>): Double {
        return produtos.sumOf { it.preco * it.quantidade }
    }
}