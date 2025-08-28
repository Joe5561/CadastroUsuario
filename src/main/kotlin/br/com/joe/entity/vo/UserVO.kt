package br.com.joe.entity.vo

import org.springframework.hateoas.RepresentationModel

data class UserVO(

    var id: Long = 0,
    var name: String = "",
    var cpf: String = "",
    var email: String = "",
    var address: String = ""

): RepresentationModel<VehicleVO>()
