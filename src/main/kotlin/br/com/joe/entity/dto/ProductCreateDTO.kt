package br.com.joe.entity.dto

import org.springframework.hateoas.RepresentationModel

data class ProductCreateDTO(

    val nome: String = "",
    val descricao: String = "",
    val preco: Double = 0.0,
    val quantidadeEstoque: Int = 0,
    val status: String = "ATIVO",
    val categoria: List<Long>

)