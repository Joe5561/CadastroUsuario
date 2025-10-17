package br.com.joe.entity.dto

import br.com.joe.entity.vo.UserVO

data class PedidoCreateDTO(
    var user: UserVO,
    var produtosIDs: List<Long>
)