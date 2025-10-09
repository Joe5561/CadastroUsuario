package br.com.joe.entity.dto

data class UserCreateDTO(

    var name: String = "",
    var cpf: String = "",
    var email: String = "",
    var telefone: String = "",
    var address: List<AddressCreateDTO>

)