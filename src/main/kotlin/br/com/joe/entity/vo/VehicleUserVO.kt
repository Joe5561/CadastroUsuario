package br.com.joe.entity.vo

import org.springframework.hateoas.RepresentationModel

data class VehicleUserVO (
    val id: Long,
    val modelo: String,
    val marca: String,
    val ano: String,
    val placa: String
): RepresentationModel<VehicleUserVO>()