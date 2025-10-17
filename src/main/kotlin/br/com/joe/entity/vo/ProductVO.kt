package br.com.joe.entity.vo

import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

data class ProductVO(

    var id: Long = 0,
    var nome: String = "",
    var descricao: String = "",
    var preco: Double = 0.0,
    var quantidadeEstoque: Int = 0,
    var status: String = "",
    var categoria: MutableList<CategoryVO> = mutableListOf(),
    var links: List<Link> = listOf(),
    var quantidade: Int = 0

): RepresentationModel<ProductVO>()