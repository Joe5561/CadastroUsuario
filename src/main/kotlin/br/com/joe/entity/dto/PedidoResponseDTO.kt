package br.com.joe.entity.dto

import br.com.joe.entity.vo.ProductVO
import br.com.joe.entity.vo.UserVO
import br.com.joe.enums.StatusPedido
import org.springframework.hateoas.RepresentationModel

data class PedidoResponseDTO(

    var numeroPedido: String = "",
    var user: UserVO = UserVO(),
    var produtos: MutableList<ProductVO> = mutableListOf(),
    var status: StatusPedido

): RepresentationModel<PedidoResponseDTO>()