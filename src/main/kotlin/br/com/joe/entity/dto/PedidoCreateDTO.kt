package br.com.joe.entity.dto

import br.com.joe.entity.vo.UserVO

data class PedidoCreateDTO(
    var cpf: String,
    var produtosIDs: List<Long>,
    var quantidadesPorProduto: Map<Long, Int> = emptyMap()
)