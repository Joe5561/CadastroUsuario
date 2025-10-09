package br.com.joe.entity.dto

import org.springframework.hateoas.RepresentationModel

data class AddressCreateDTO(

    var logradouro: String = "",
    var numero: String = "",
    var complemento: String = "",
    var bairro: String = "",
    var cep: String = ""

): RepresentationModel<AddressCreateDTO>()