package br.com.joe.entity.vo

import org.springframework.hateoas.RepresentationModel

data class VehicleVO(
    var id: Long = 0,
    var modelo: String = "",
    var marca: String = "",
    var ano: String = "",
    var placa: String = ""
): RepresentationModel<VehicleVO>()
