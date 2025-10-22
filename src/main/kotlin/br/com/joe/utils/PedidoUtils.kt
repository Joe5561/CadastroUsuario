package br.com.joe.utils

import br.com.joe.entity.vo.ProductVO
import br.com.joe.entity.vo.UserVO
import br.com.joe.exception.InsufficientStockException
import br.com.joe.repository.PedidoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.fasterxml.jackson.core.type.TypeReference

@Component
class PedidoUtils {
    @Autowired
    private lateinit var pedidoRepository: PedidoRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    fun gerarNumeroPedidoUnico(): String {
        var numero: String
        do {
            numero = (100000..999999).random().toString()
        } while (pedidoRepository.existsByNumeroPedido(numero))
        return numero
    }

    fun calcularValorTotal(produtos: List<ProductVO>): Double {
        val produtosComErro = produtos
            .filter { it.quantidadeEstoque < it.quantidade }
            .map { it.nome }
        if (produtosComErro.isNotEmpty()) {
            throw InsufficientStockException(
                "Estoque insuficiente para os produtos:" +
                        " ${produtosComErro.joinToString(", ")}")
        }
        produtos.forEach { it.quantidadeEstoque -= it.quantidade }
        return produtos.sumOf { it.preco * it.quantidade }
    }

    fun desserializarPedidoJson(userJson: String, produtosJson: String, numeroPedido: String): Pair<UserVO, MutableList<ProductVO>> {
        if (userJson.isBlank()) {
            throw IllegalStateException("Campo userJson está vazio no pedido $numeroPedido")
        }
        if (produtosJson.isBlank()) {
            throw IllegalStateException("Campo produtosJson está vazio no pedido $numeroPedido")
        }
        return try {
            val userVO = objectMapper.readValue(userJson, UserVO::class.java)
            val produtosVO = objectMapper.readValue(
                produtosJson,
                object : TypeReference<MutableList<ProductVO>>() {}
            )
            Pair(userVO, produtosVO)
        } catch (e: Exception) {
            throw RuntimeException("Erro ao desserializar JSON do pedido $numeroPedido: ${e.message}", e)
        }
    }
}