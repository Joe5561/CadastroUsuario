package br.com.joe.entity.dto

import org.springframework.hateoas.RepresentationModel

data class ProductResponseDTOList(

    var id: Long = 0,
    var nome: String = "",
    var descricao: String = "",
    var preco: Double = 0.0,
    var quantidadeEstoque: Int = 0,
    var status: String = "",
    var categoria: List<CategoryResponseDTO>

): RepresentationModel<ProductResponseDTOList>()