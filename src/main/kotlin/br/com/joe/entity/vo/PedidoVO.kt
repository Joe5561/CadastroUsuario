package br.com.joe.entity.vo

import br.com.joe.enums.StatusPedido

data class PedidoVO(

    var numeroPedido: String = "",
    var user: UserVO = UserVO(),
    var produtos: List<ProductVO> = mutableListOf(),
    var status: StatusPedido = StatusPedido.RECEBIDO,
    var quantidade: Int = 0
)