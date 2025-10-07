package br.com.joe.entity.vo
import org.springframework.hateoas.RepresentationModel

data class AddressVO(

    var id: Long = 0,
    var logradouro: String = "",
    var numero: String = "",
    var complemento: String = "",
    var bairro: String = "",
    var cep: String = ""

): RepresentationModel<AddressVO>()
