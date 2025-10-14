package br.com.joe.entity.dto

data class ProductCreateDTO(

    var nome: String = "",
    var descricao: String = "",
    var preco: Double = 0.0,
    var quantidadeEstoque: Int = 0,
    var status: String = "",
    var categoria: List<Long>

)