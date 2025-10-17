package br.com.joe.entity.vo

data class PedidoVO(

    var numeroPedido: String = "",
    var user: UserVO = UserVO(),
    var produtos: List<ProductVO> = mutableListOf()
)