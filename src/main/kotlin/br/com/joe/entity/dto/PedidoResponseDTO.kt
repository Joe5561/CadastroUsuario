package br.com.joe.entity.dto

import br.com.joe.entity.vo.ProductVO
import br.com.joe.entity.vo.UserVO
import org.springframework.hateoas.RepresentationModel

data class PedidoResponseDTO(

    var numeroPedido: String = "",
    var user: UserVO = UserVO(),
    var produtos: MutableList<ProductVO> = mutableListOf()

): RepresentationModel<PedidoResponseDTO>()