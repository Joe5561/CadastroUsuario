package br.com.joe.entity.dto

import br.com.joe.entity.vo.AddressVO

data class AddressDTO(

    var cpfOuCnpj: String = "",
    var novoEndereco: AddressVO

)