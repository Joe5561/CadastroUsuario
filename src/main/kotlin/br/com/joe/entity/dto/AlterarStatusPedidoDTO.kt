package br.com.joe.entity.dto

import br.com.joe.enums.StatusPedido

data class AlterarStatusPedidoDTO(

    val identificador: String = "",
    val novoStatus: StatusPedido

)
